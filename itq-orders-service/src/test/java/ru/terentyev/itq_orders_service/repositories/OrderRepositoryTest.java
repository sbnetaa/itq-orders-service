package ru.terentyev.itq_orders_service.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.terentyev.itq_orders_service.entities.Order;
import ru.terentyev.itq_orders_service.entities.OrderDetails;

import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OrderRepositoryTest {

    private JdbcTemplate jdbcTemplate;
    private OrderRepository orderRepository;
    private Order order;

    @BeforeEach
    void setUp() {
        jdbcTemplate = mock(JdbcTemplate.class);
        orderRepository = new OrderRepository(jdbcTemplate);
        prepareOrder();
    }

    private void prepareOrder(){
        order = new Order();
        order.setNumber("123456");
        order.setCost(100);
        order.setDate(LocalDate.now());
        order.setReceiver("Receiver Name");
        order.setAddress("Receiver Address");
        order.setPaymentType("CREDIT");
        order.setDeliveryType("STANDARD");
    }

    @Test
    void save_ShouldInsertOrderAndReturnOrderWithGeneratedId() {
        when(jdbcTemplate.queryForObject(anyString(), eq(Long.class), any(Object[].class))).thenReturn(1L);
        Order savedOrder = orderRepository.save(order);
        assertNotNull(savedOrder.getId());
        assertEquals("123456", savedOrder.getNumber());
        verify(jdbcTemplate, times(1)).queryForObject(anyString(), eq(Long.class), any(Object[].class));
    }

    @Test
    void findById_ShouldReturnOrder_WhenExists() {
        when(jdbcTemplate.queryForObject(anyString(), any(OrderRepository.OrderRowMapper.class), eq(1L)))
                .thenReturn(order);
        Order actualOrder = orderRepository.findById(1L);
        assertEquals(order, actualOrder);
        verify(jdbcTemplate, times(1)).queryForObject(anyString(), any(OrderRepository.OrderRowMapper.class), eq(1L));
    }

    @Test
    void searchBySumAndDate_ShouldReturnMap_WhenOrdersExists() {
        int minSum = 50;
        LocalDate testDate = LocalDate.now();
        Map<Order, OrderDetails> result = orderRepository.searchBySumAndDate(minSum, testDate);
        assertNotNull(result);
        boolean resultsValid = result.keySet().stream()
                .allMatch(order -> order.getDate().isEqual(testDate)
                        && order.getCost() > minSum);
        assertTrue(resultsValid);
        verify(jdbcTemplate, times(1)).query(anyString(), any(OrderRepository.MixedRowMapper.class), eq(minSum), any());
    }

    @Test
    void searchByArticleAndMissingAndDate_ShouldReturnMap() {
        int article = 101;
        LocalDate dateFrom = LocalDate.now().minusDays(1);
        LocalDate dateTo = LocalDate.now();
        Map<Order, OrderDetails> result = orderRepository.searchByArticleAndMissingAndDate(article, dateFrom, dateTo);
        assertNotNull(result);
        verify(jdbcTemplate, times(1)).query(anyString(), any(OrderRepository.MixedRowMapper.class), eq(article), eq(dateFrom), eq(dateTo));
    }
}
