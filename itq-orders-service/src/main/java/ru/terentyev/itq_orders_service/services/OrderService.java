package ru.terentyev.itq_orders_service.services;

import ru.terentyev.itq_orders_service.schemas.OrderRequestSchema;
import ru.terentyev.itq_orders_service.schemas.OrderResponseSchema;

import java.util.List;

public interface OrderService {
    OrderResponseSchema createOrder(OrderRequestSchema request);
    OrderResponseSchema takeSingleOrder(Long id);
    List<OrderResponseSchema> search(OrderRequestSchema request);
}
