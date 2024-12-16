package ru.terentyev.itq_orders_service.services;

import ru.terentyev.itq_orders_service.entities.OrderRequest;
import ru.terentyev.itq_orders_service.entities.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(OrderRequest request);
    OrderResponse takeSingleOrder(Long id);
    List<OrderResponse> search(OrderRequest request);
}
