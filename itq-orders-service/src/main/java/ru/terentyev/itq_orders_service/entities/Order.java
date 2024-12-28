package ru.terentyev.itq_orders_service.entities;

import ru.terentyev.itq_orders_service.schemas.OrderSchema;

import java.io.Serializable;
import java.util.Objects;

public class Order extends OrderSchema implements Serializable {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Order order = (Order) o;
        return Objects.equals(getNumber(), order.getNumber())
                && Objects.equals(getCost(), order.getCost())
                && Objects.equals(getDate(), order.getDate())
                && Objects.equals(getReceiver(), order.getReceiver())
                && Objects.equals(getAddress(), order.getAddress())
                && Objects.equals(getPaymentType(), order.getPaymentType())
                && Objects.equals(getDeliveryType(), order.getDeliveryType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getNumber(), getCost()
                , getDate(), getReceiver(), getAddress(), getPaymentType(), getDeliveryType());
    }
}
