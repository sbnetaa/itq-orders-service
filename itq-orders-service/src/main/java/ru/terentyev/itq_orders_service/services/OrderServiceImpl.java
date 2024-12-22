package ru.terentyev.itq_orders_service.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.terentyev.itq_orders_service.entities.NumberObject;
import ru.terentyev.itq_orders_service.entities.Order;
import ru.terentyev.itq_orders_service.entities.OrderDetails;
import ru.terentyev.itq_orders_service.entities.OrderResponse;
import ru.terentyev.itq_orders_service.entities.Product;
import ru.terentyev.itq_orders_service.exceptions.ProductNotExistsException;
import ru.terentyev.itq_orders_service.repositories.OrderDetailsRepository;
import ru.terentyev.itq_orders_service.repositories.OrderRepository;
import ru.terentyev.itq_orders_service.repositories.ProductRepository;
import ru.terentyev.itq_orders_service.schemas.OrderRequestSchema;
import ru.terentyev.itq_orders_service.schemas.OrderResponseSchema;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailsRepository orderDetailsRepository;
    private final ProductRepository productRepository;
    private final RestTemplate restTemplate;
    @Value("${itq.numbers-service.url}")
    private String numbersServiceUrl;
    @Value("${itq.numbers-service.port}")
    private String numbersServicePort;

    public OrderServiceImpl(OrderRepository orderRepository
            , OrderDetailsRepository orderDetailsRepository, ProductRepository productRepository
            , RestTemplate restTemplate) {
        this.orderRepository = orderRepository;
        this.orderDetailsRepository = orderDetailsRepository;
        this.productRepository = productRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public OrderResponseSchema createOrder(OrderRequestSchema request) {
        Product product = productRepository.findByArticle(request.getArticle());
        if (product == null) {
            throw new ProductNotExistsException(request.getArticle());
        }
        Order order = orderRepository.save(mapToOrder(request, product));
        OrderDetails orderDetails = orderDetailsRepository.save(mapToOrderDetails(order, request, product));
        return mapToOrderResponse(order, orderDetails);
    }

    @Override
    public OrderResponse takeSingleOrder(Long id) {
        Order order = orderRepository.findById(id);
        OrderDetails orderDetails = orderDetailsRepository.findByOrderId(id);
        return mapToOrderResponse(order, orderDetails);
    }

    @Override
    public List<OrderResponseSchema> search(OrderRequestSchema request) {
        Map<Order, OrderDetails> orderMap;
        if (request.getSum() != null) {
            orderMap = orderRepository.searchBySumAndDate(request.getSum(), request.getDate());
        } else if (request.getArticle() != null) {
            orderMap = orderRepository.searchByArticleAndMissingAndDate(request.getArticle(), request.getDateFrom(), request.getDateTo());
        } else {
            throw new RuntimeException("Некорректный запрос на поиск");
        }
        List<OrderResponseSchema> responseList = new ArrayList<>();
        orderMap.forEach((o, od) -> responseList.add(mapToOrderResponse(o, od)));
        return responseList;
    }

    private NumberObject requestNumber() {
        String url = "http://" + numbersServiceUrl + ":"
                + numbersServicePort + "/api/v1/numbers/generate";
        ResponseEntity<NumberObject> response = restTemplate.getForEntity(url, NumberObject.class);
        return response.getBody();
    }

    private Order mapToOrder(OrderRequestSchema request, Product product) {
        Order newOrder = new Order();
        newOrder.setNumber(requestNumber().getNumber());
        newOrder.setCost(product.getPrice() * request.getAmount());
        newOrder.setAddress(request.getAddress());
        newOrder.setDate(LocalDate.now());
        newOrder.setReceiver(request.getReceiver());
        newOrder.setDeliveryType(request.getDeliveryType());
        newOrder.setPaymentType(request.getPaymentType());
        return newOrder;
    }

    private OrderDetails mapToOrderDetails(Order order, OrderRequestSchema request, Product product) {
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setOrderId(order.getId());
        orderDetails.setAmount(request.getAmount());
        orderDetails.setTitle(product.getTitle());
        orderDetails.setPrice(product.getPrice());
        orderDetails.setArticle(request.getArticle());
        return orderDetails;
    }

    private OrderResponse mapToOrderResponse(Order order, OrderDetails orderDetails){
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setAddress(order.getAddress());
        response.setAmount(orderDetails.getAmount());
        response.setDate(order.getDate());
        response.setArticle(orderDetails.getArticle());
        response.setPrice(orderDetails.getPrice());
        response.setCost(order.getCost());
        response.setDeliveryType(order.getDeliveryType());
        response.setPaymentType(order.getPaymentType());
        response.setTitle(orderDetails.getTitle());
        response.setReceiver(order.getReceiver());
        response.setNumber(order.getNumber());
        return response;
    }
}
