package ru.terentyev.itq_orders_service.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;
import ru.terentyev.itq_orders_service.schemas.OrderRequestSchema;
import ru.terentyev.itq_orders_service.schemas.OrderResponseSchema;
import ru.terentyev.itq_orders_service.entities.Order;
import ru.terentyev.itq_orders_service.entities.OrderDetails;
import ru.terentyev.itq_orders_service.entities.Product;
import ru.terentyev.itq_orders_service.exceptions.ProductNotExistsException;
import ru.terentyev.itq_orders_service.repositories.OrderRepository;
import ru.terentyev.itq_orders_service.repositories.OrderDetailsRepository;
import ru.terentyev.itq_orders_service.repositories.ProductRepository;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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
    private WebClient.Builder webClientBuilder;
    private WebClient webClient;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        webClient = Mockito.mock(WebClient.class);
        when(webClientBuilder.build()).thenReturn(webClient);
    }

    @Test
    public void testCreateOrder() {
        OrderRequestSchema request = new OrderRequestSchema();
        request.setArticle(341);
        request.setAmount(3);
        request.setAddress("Москва, улица Пржевальского");
        request.setReceiver("Дмитрий");
        request.setDeliveryType("Курьером");
        request.setDeliveryType("Наличкой");

        Product product = new Product();
        product.setPrice(500);
        product.setTitle("Скатерть");
        when(productRepository.findByArticle(request.getArticle())).thenReturn(product);
        Order order = new Order();
        order.setId(1L);
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setOrderId(order.getId());
        when(orderDetailsRepository.save(any(OrderDetails.class))).thenReturn(orderDetails);
        OrderResponseSchema response = orderService.createOrder(request);
        response.setCost(1500);
        response.setArticle(341);
        response.setAddress("Москва, улица Пржевальского");
        assertThat(response).isNotNull();
        assertThat(response.getCost()).isEqualTo(product.getPrice() * request.getAmount());
        assertThat(response.getArticle()).isEqualTo(request.getArticle());
        assertThat(response.getAddress()).isEqualTo(request.getAddress());
    }

    @Test
    public void testCreateOrder_ProductNotFound() {
        OrderRequestSchema request = new OrderRequestSchema();
        request.setArticle(404);
        when(productRepository.findByArticle(request.getArticle())).thenReturn(null);
        ProductNotExistsException thrown =
                assertThrows(ProductNotExistsException.class, () -> orderService.createOrder(request));
        assertThat(thrown.getMessage()).isEqualTo("Товар с артикулом " + request.getArticle() + " не найден.");
    }

    @Test
    public void testTakeSingleOrder() {
        Long orderId = 1L;
        Order order = new Order();
        order.setId(orderId);
        order.setAddress("Москва, улица Пржевальского");
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setOrderId(orderId);
        orderDetails.setPrice(100);
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
        Order order = new Order();
        order.setId(1L);
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setOrderId(order.getId());
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
        Order order = new Order();
        order.setId(1L);
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setOrderId(order.getId());
        Map<Order, OrderDetails> orderMap = new HashMap<>();
        orderMap.put(order, orderDetails);
        when(orderRepository.searchByArticleMissingAndDate(request.getArticle(), request.getDateFrom(), request.getDateTo()))
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
