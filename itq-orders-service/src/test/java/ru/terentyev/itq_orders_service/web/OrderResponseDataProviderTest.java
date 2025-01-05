package ru.terentyev.itq_orders_service.web;

import org.apache.wicket.model.IModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.terentyev.itq_orders_service.entities.OrderResponseSchemaWrapper;
import ru.terentyev.itq_orders_service.schemas.OrderRequestSchema;
import ru.terentyev.itq_orders_service.schemas.OrderResponseSchema;
import ru.terentyev.itq_orders_service.services.OrderService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderResponseDataProviderTest {

    private OrderService orderService;
    private OrderResponseDataProvider dataProvider;

    @BeforeEach
    public void setUp() {
        orderService = mock(OrderService.class);
        dataProvider = new OrderResponseDataProvider(orderService);
    }

    @Test
    public void testUpdateDataWithNullRequest() {
        dataProvider.updateData(null);
        assertTrue(dataProvider.size() == 0, "Список должен быть пуст при null-запросе.");
    }

    @Test
    public void testUpdateDataWithIdRequest() {
        OrderResponseSchema response = new OrderResponseSchema();
        when(orderService.takeSingleOrder(1L)).thenReturn(response);
        OrderRequestSchema request = new OrderRequestSchema();
        request.setId(1L);
        dataProvider.updateData(request);
        assertEquals(1, dataProvider.size());
        assertEquals(response, dataProvider.iterator(0, 1).next().getOrderResponseSchema());
        verify(orderService).takeSingleOrder(1L);
    }

    @Test
    public void testUpdateDataWithSearchRequest() {
        List<OrderResponseSchema> mockResults = Arrays.asList(
                new OrderResponseSchema(),
                new OrderResponseSchema()
        );
        when(orderService.search(Mockito.any())).thenReturn(mockResults);
        OrderRequestSchema request = new OrderRequestSchema();
        request.setSum(100);
        request.setDate(LocalDate.now());
        dataProvider.updateData(request);
        assertEquals(2, dataProvider.size());
        Iterator<? extends OrderResponseSchemaWrapper> iterator = dataProvider.iterator(0, 2);
        assertTrue(iterator.hasNext());
        assertEquals(mockResults.get(0), iterator.next().getOrderResponseSchema());
        assertEquals(mockResults.get(1), iterator.next().getOrderResponseSchema());
        verify(orderService).search(request);
    }

    @Test
    public void testUpdateDataWithCreateOrderRequest() {
        OrderResponseSchema mockResponse = new OrderResponseSchema();
        when(orderService.createOrder(Mockito.any())).thenReturn(mockResponse);
        OrderRequestSchema request = new OrderRequestSchema();
        dataProvider.updateData(request);
        assertEquals(1, dataProvider.size());
        assertEquals(mockResponse, dataProvider.iterator(0, 1).next().getOrderResponseSchema());
        verify(orderService).createOrder(request);
    }

    @Test
    public void testIteratorReturnsCorrectSubset() {
        List<OrderResponseSchemaWrapper> mockList = Arrays.asList(
                new OrderResponseSchemaWrapper(new OrderResponseSchema()),
                new OrderResponseSchemaWrapper(new OrderResponseSchema()),
                new OrderResponseSchemaWrapper(new OrderResponseSchema())
        );
        dataProvider.setResponsesList(mockList);
        Iterator<? extends OrderResponseSchemaWrapper> iterator = dataProvider.iterator(1, 1);
        assertTrue(iterator.hasNext());
        assertSame(mockList.get(1), iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testSizeReturnsCorrectValue() {
        List<OrderResponseSchemaWrapper> mockList = Arrays.asList(
                new OrderResponseSchemaWrapper(new OrderResponseSchema()),
                new OrderResponseSchemaWrapper(new OrderResponseSchema())
        );
        dataProvider.setResponsesList(mockList);
        assertEquals(2, dataProvider.size());
    }

    @Test
    public void testModelReturnsCorrectModel() {
        OrderResponseSchemaWrapper response = new OrderResponseSchemaWrapper(new OrderResponseSchema());
        IModel<OrderResponseSchemaWrapper> model = dataProvider.model(response);
        assertNotNull(model);
        assertEquals(response, model.getObject());
    }
}
