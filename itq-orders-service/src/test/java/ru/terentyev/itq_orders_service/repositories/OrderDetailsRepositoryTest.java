package ru.terentyev.itq_orders_service.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.terentyev.itq_orders_service.entities.OrderDetails;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OrderDetailsRepositoryTest {

    private JdbcTemplate jdbcTemplate;
    private OrderDetailsRepository orderDetailsRepository;
    private OrderDetails orderDetails;

    @BeforeEach
    void setUp() {
        jdbcTemplate = mock(JdbcTemplate.class);
        orderDetailsRepository = new OrderDetailsRepository(jdbcTemplate);
        prepareOrderDetails();
    }

    private void prepareOrderDetails(){
        orderDetails = new OrderDetails();
        orderDetails.setArticle(123);
        orderDetails.setTitle("Название");
        orderDetails.setAmount(10);
        orderDetails.setPrice(100);
        orderDetails.setOrderId(1L);
    }

    @Test
    void save_ShouldInsertOrderDetailsAndReturnOrderDetailsWithGeneratedId() {
        when(jdbcTemplate.queryForObject(anyString(), eq(Long.class),
                any(Object[].class))).thenReturn(1L);
        OrderDetails savedOrderDetails = orderDetailsRepository.save(orderDetails);
        assertNotNull(savedOrderDetails.getId());
        assertEquals(1L, savedOrderDetails.getId());
        assertEquals(123, savedOrderDetails.getArticle());
        assertEquals("Название", savedOrderDetails.getTitle());
        verify(jdbcTemplate, times(1)).queryForObject(anyString(), eq(Long.class),
                any(Object[].class));
    }

    @Test
    void findByOrderId_ShouldReturnOrderDetails_WhenExists() {
        when(jdbcTemplate.queryForObject(anyString(), any(OrderDetailsRepository.OrderDetailsRowMapper.class), eq(1L)))
                .thenReturn(orderDetails);
        OrderDetails actualOrderDetails = orderDetailsRepository.findByOrderId(1L);
        assertEquals(orderDetails.getId(), actualOrderDetails.getId());
        assertEquals(orderDetails.getArticle(), actualOrderDetails.getArticle());
        assertEquals(orderDetails.getOrderId(), actualOrderDetails.getOrderId());
        assertEquals(orderDetails.getPrice(), actualOrderDetails.getPrice());
        assertEquals(orderDetails.getAmount(), actualOrderDetails.getAmount());
        assertEquals(orderDetails.getTitle(), actualOrderDetails.getTitle());
        verify(jdbcTemplate, times(1)).queryForObject(anyString(), any(OrderDetailsRepository.OrderDetailsRowMapper.class), eq(1L));
    }
}
