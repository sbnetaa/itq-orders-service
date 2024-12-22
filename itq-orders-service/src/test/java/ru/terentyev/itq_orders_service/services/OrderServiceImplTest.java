package ru.terentyev.itq_orders_service.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.terentyev.itq_orders_service.entities.NumberObject;
import ru.terentyev.itq_orders_service.entities.Order;
import ru.terentyev.itq_orders_service.entities.OrderDetails;
import ru.terentyev.itq_orders_service.entities.Product;
import ru.terentyev.itq_orders_service.exceptions.ProductNotExistsException;
import ru.terentyev.itq_orders_service.repositories.OrderDetailsRepository;
import ru.terentyev.itq_orders_service.repositories.OrderRepository;
import ru.terentyev.itq_orders_service.repositories.ProductRepository;
import ru.terentyev.itq_orders_service.schemas.OrderRequestSchema;
import ru.terentyev.itq_orders_service.schemas.OrderResponseSchema;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderDetailsRepository orderDetailsRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private RestTemplate restTemplate;
    private Order order;
    private OrderDetails orderDetails;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        prepareOrder();
    }

    private void prepareOrder(){
        Long orderId = 1L;
        order = new Order();
        order.setId(orderId);
        order.setAddress("Москва, улица Пржевальского");
        order.setCost(1500);
        order.setNumber("1234520241222");
        order.setDate(LocalDate.now());
        order.setPaymentType("Наличкой");
        order.setDeliveryType("Курьером");
        order.setReceiver("Дмитрий");
        orderDetails = new OrderDetails();
        orderDetails.setOrderId(orderId);
        orderDetails.setPrice(500);
        orderDetails.setArticle(341);
        orderDetails.setAmount(3);
        orderDetails.setTitle("Скатерть");
    }

    @Test
    public void testCreateOrder() {
        OrderRequestSchema request = new OrderRequestSchema();
        request.setArticle(341);
        request.setAmount(3);
        request.setAddress("Москва, улица Пржевальского");
        request.setReceiver("Дмитрий");
        request.setDeliveryType("Курьером");
        request.setPaymentType("Наличкой");

        Product product = new Product();
        product.setPrice(500);
        product.setTitle("Скатерть");
        NumberObject numberObject = new NumberObject();
        numberObject.setNumber("1234520241222");
        when(productRepository.findByArticle(request.getArticle())).thenReturn(product);
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(orderDetailsRepository.save(any(OrderDetails.class))).thenReturn(orderDetails);
        when(restTemplate.getForEntity(any(String.class), eq(NumberObject.class))).thenReturn(new ResponseEntity<>(numberObject, HttpStatus.OK));
        OrderResponseSchema response = orderService.createOrder(request);
        assertThat(response).isNotNull();
        assertThat(response.getCost()).isEqualTo(product.getPrice() * request.getAmount());
        assertThat(response.getArticle()).isEqualTo(request.getArticle());
        assertThat(response.getAddress()).isEqualTo(request.getAddress());
        assertThat(response.getPrice()).isEqualTo(product.getPrice());
        assertThat(response.getAmount()).isEqualTo(request.getAmount());
        assertThat(response.getDate()).isEqualTo(LocalDate.now());
        assertThat(response.getDeliveryType()).isEqualTo(request.getDeliveryType());
        assertThat(response.getPaymentType()).isEqualTo(request.getPaymentType());
        assertThat(response.getReceiver()).isEqualTo(request.getReceiver());
        assertThat(response.getNumber()).isEqualTo(numberObject.getNumber());
        assertThat(response.getTitle()).isEqualTo(product.getTitle());
    }

    @Test
    public void testCreateOrder_ProductNotFound() {
        OrderRequestSchema request = new OrderRequestSchema();
        request.setArticle(404);
        when(productRepository.findByArticle(request.getArticle())).thenReturn(null);
        ProductNotExistsException thrown =
                assertThrows(ProductNotExistsException.class, () -> orderService.createOrder(request));
        assertThat(thrown.getClass()).isEqualTo(ProductNotExistsException.class);
    }

    @Test
    public void testTakeSingleOrder() {
        Long orderId = 1L;
        when(orderRepository.findById(orderId)).thenReturn(order);
        when(orderDetailsRepository.findByOrderId(orderId)).thenReturn(orderDetails);
        OrderResponseSchema response = orderService.takeSingleOrder(orderId);
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(orderId);
        assertThat(response.getAddress()).isEqualTo(order.getAddress());
        assertThat(response.getPrice()).isEqualTo(orderDetails.getPrice());
    }

    @Test
    public void testSearch_BySum() {
        OrderRequestSchema request = new OrderRequestSchema();
        request.setSum(200);
        request.setDate(LocalDate.now());
        Map<Order, OrderDetails> orderMap = new HashMap<>();
        orderMap.put(order, orderDetails);
        when(orderRepository.searchBySumAndDate(request.getSum(), request.getDate()))
                .thenReturn(orderMap);
        List<OrderResponseSchema> responseList = orderService.search(request);
        assertThat(responseList).isNotEmpty();
        assertThat(responseList.getFirst().getId()).isEqualTo(order.getId());
        assertThat(responseList.getFirst().getPrice()).isEqualTo(orderDetails.getPrice());
    }

    @Test
    public void testSearch_ByArticle() {
        OrderRequestSchema request = new OrderRequestSchema();
        request.setArticle(838);
        request.setDateFrom(LocalDate.now().minusDays(5));
        request.setDateTo(LocalDate.now());
        Map<Order, OrderDetails> orderMap = new HashMap<>();
        orderMap.put(order, orderDetails);
        when(orderRepository.searchByArticleAndMissingAndDate(request.getArticle(), request.getDateFrom(), request.getDateTo()))
                .thenReturn(orderMap);
        List<OrderResponseSchema> responseList = orderService.search(request);
        assertThat(responseList).isNotEmpty();
        assertThat(responseList.get(0).getId()).isEqualTo(order.getId());
        assertThat(responseList.get(0).getPrice()).isEqualTo(orderDetails.getPrice());
    }

    @Test
    public void testSearch_InvalidRequest() {
        OrderRequestSchema request = new OrderRequestSchema();
        RuntimeException thrown =
                assertThrows(RuntimeException.class, () -> orderService.search(request));
        assertThat(thrown.getMessage()).isEqualTo("Некорректный запрос на поиск");
    }
}
