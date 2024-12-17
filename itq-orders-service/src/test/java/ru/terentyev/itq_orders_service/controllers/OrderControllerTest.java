package ru.terentyev.itq_orders_service.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ru.terentyev.itq_orders_service.entities.OrderRequest;
import ru.terentyev.itq_orders_service.entities.OrderResponse;
import ru.terentyev.itq_orders_service.schemas.OrderRequestSchema;
import ru.terentyev.itq_orders_service.schemas.OrderResponseSchema;
import ru.terentyev.itq_orders_service.services.OrderService;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;
    @Mock
    private OrderService orderService;
    private OrderResponse response;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        fillObjects();
    }

    private void fillObjects(){
        response = new OrderResponse();
        response.setTitle("Сапоги");
        response.setPrice(600);
        response.setReceiver("Дмитрий");
        response.setCost(1800);
        response.setDate(LocalDate.now());
        response.setArticle(341);
        response.setAddress("Москва, улица Пржевальского");
        response.setPaymentType("Наличкой");
        response.setDeliveryType("Курьером");
    }

    @Test
    public void testCreateOrder() {
        OrderRequest request = new OrderRequest();
        request.setAddress("Москва, улица Пржевальского");
        request.setAmount(3);
        request.setArticle(341);
        request.setDeliveryType("Курьером");
        request.setPaymentType("Наличкой");
        request.setReceiver("Дмитрий");

        when(orderService.createOrder(request)).thenReturn(response);
        ResponseEntity<OrderResponseSchema> result = orderController.createOrder(request);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isEqualTo(response);
    }

    @Test
    public void testTakeSingleOrder() {
        Long orderId = 1L;
        when(orderService.takeSingleOrder(orderId)).thenReturn(response);
        ResponseEntity<OrderResponseSchema> result = orderController.takeSingleOrder(orderId);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
    }

    @Test
    public void testSearch() {
        OrderRequestSchema request = new OrderRequestSchema();
        request.setDate(LocalDate.now());
        request.setSum(850);
        List<OrderResponseSchema> responses = Collections.singletonList(response);
        when(orderService.search(request)).thenReturn(responses);
        ResponseEntity<List<OrderResponseSchema>> result = orderController.searchOrders(request);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(responses);
    }
}