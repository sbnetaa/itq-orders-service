package ru.terentyev.itq_orders_service.entities;

import ru.terentyev.itq_orders_service.schemas.OrderDetailsSchema;

import java.util.Objects;

public class OrderDetails extends OrderDetailsSchema {


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        OrderDetails that = (OrderDetails) o;
        return Objects.equals(getArticle(), that.getArticle()) && Objects.equals(getTitle(), that.getTitle())
                && Objects.equals(getAmount(), that.getAmount())
                && Objects.equals(getPrice(), that.getPrice())
                && Objects.equals(getOrderId(), that.getOrderId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getArticle(), getTitle(), getAmount(), getPrice(), getOrderId());
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "article=" + getArticle() +
                ", title='" + getTitle() + '\'' +
                ", amount=" + getAmount() +
                ", price=" + getPrice() +
                ", orderId=" + getOrderId() +
                '}';
    }
}
