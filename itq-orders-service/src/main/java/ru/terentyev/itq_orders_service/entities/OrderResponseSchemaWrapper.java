package ru.terentyev.itq_orders_service.entities;

import ru.terentyev.itq_orders_service.schemas.OrderResponseSchema;

import java.util.Objects;

public class OrderResponseSchemaWrapper extends AbstractEntity {
    private OrderResponseSchema orderResponseSchema;

    public OrderResponseSchemaWrapper(OrderResponseSchema orderResponseSchema) {
        this.orderResponseSchema = orderResponseSchema;
    }

    public OrderResponseSchema getOrderResponseSchema() {
        return orderResponseSchema;
    }

    public void setOrderResponseSchema(OrderResponseSchema orderResponseSchema) {
        this.orderResponseSchema = orderResponseSchema;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        OrderResponseSchemaWrapper that = (OrderResponseSchemaWrapper) o;
        return Objects.equals(orderResponseSchema, that.orderResponseSchema);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), orderResponseSchema);
    }
}
