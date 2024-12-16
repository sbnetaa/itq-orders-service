package ru.terentyev.itq_orders_service.services;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.terentyev.itq_orders_service.entities.Order;
import ru.terentyev.itq_orders_service.entities.OrderDetails;
import ru.terentyev.itq_orders_service.entities.OrderRequest;
import ru.terentyev.itq_orders_service.entities.OrderResponse;
import ru.terentyev.itq_orders_service.entities.Product;
import ru.terentyev.itq_orders_service.exceptions.ProductNotExistsException;
import ru.terentyev.itq_orders_service.repositories.OrderDetailsRepository;
import ru.terentyev.itq_orders_service.repositories.OrderRepository;
import ru.terentyev.itq_orders_service.repositories.ProductRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailsRepository orderDetailsRepository;
    private final ProductRepository productRepository;
    private final WebClient webClient;

    public OrderServiceImpl(OrderRepository orderRepository
            , OrderDetailsRepository orderDetailsRepository, ProductRepository productRepository
            , WebClient.Builder webClientBuilder) {
        this.orderRepository = orderRepository;
        this.orderDetailsRepository = orderDetailsRepository;
        this.productRepository = productRepository;
        this.webClient = webClientBuilder.build();
    }

    @Override
    public OrderResponse createOrder(OrderRequest request) {
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
    public List<OrderResponse> search(OrderRequest request) {
        Map<Order, OrderDetails> orderMap;
        if (request.getSum() != null) {
            orderMap = orderRepository.searchBySumAndDate(request.getSum(), request.getDate());
        } else if (request.getArticle() != null) {
            orderMap = orderRepository.searchByArticleMissingAndDate(request.getArticle(), request.getDateFrom(), request.getDateTo());
        } else {
            throw new RuntimeException("Некорректный запрос на поиск");
        }
        List<OrderResponse> responseList = new ArrayList<>();
        orderMap.forEach((o, od) -> responseList.add(mapToOrderResponse(o, od)));
        return responseList;
    }

    private Order mapToOrder(OrderRequest request, Product product) {
        Order newOrder = new Order();
        newOrder.setNumber(String.valueOf((int) (Math.random() * 100000))); // TODO
        newOrder.setCost(product.getPrice() * request.getAmount());
        newOrder.setAddress(request.getAddress());
        newOrder.setDate(LocalDate.now());
        newOrder.setReceiver(request.getReceiver());
        newOrder.setDeliveryType(request.getDeliveryType());
        newOrder.setPaymentType(request.getPaymentType());
        return newOrder;
    }

    private OrderDetails mapToOrderDetails(Order order, OrderRequest request, Product product) {
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
