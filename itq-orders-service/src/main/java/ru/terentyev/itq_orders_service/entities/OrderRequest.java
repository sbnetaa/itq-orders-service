package ru.terentyev.itq_orders_service.entities;

import ru.terentyev.itq_orders_service.schemas.OrderRequestSchema;

import java.io.Serializable;
import java.util.Objects;

public class OrderRequest extends OrderRequestSchema implements Serializable {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        OrderRequest that = (OrderRequest) o;
        return Objects.equals(getArticle(), that.getArticle())
                && Objects.equals(getAmount(), that.getAmount())
                && Objects.equals(getReceiver(), that.getReceiver())
                && Objects.equals(getAddress(), that.getAddress())
                && Objects.equals(getPaymentType(), that.getPaymentType())
                && Objects.equals(getDeliveryType(), that.getDeliveryType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId(), getArticle(), getAmount(), getReceiver(), getAddress(), getPaymentType(), getDeliveryType());
    }

    @Override
    public String toString() {
        return "OrderRequest{" +
                "article=" + getArticle() +
                ", amount=" + getAmount() +
                ", receiver=" + getReceiver() +
                ", address=" + getAddress() +
                ", paymentType=" + getPaymentType() +
                ", deliveryType=" + getDeliveryType() +
                '}';
    }
}
