package ru.terentyev.itq_orders_service.entities;

import ru.terentyev.itq_orders_service.schemas.OrderResponseSchema;

import java.util.Objects;

public class OrderResponse extends OrderResponseSchema {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        OrderResponse orderResponse = (OrderResponse) o;
        return Objects.equals(getNumber(), orderResponse.getNumber())
                && Objects.equals(getId(), orderResponse.getId())
                && Objects.equals(getTitle(), orderResponse.getTitle())
                && Objects.equals(getCost(), orderResponse.getCost())
                && Objects.equals(getDate(), orderResponse.getDate())
                && Objects.equals(getReceiver(), orderResponse.getReceiver())
                && Objects.equals(getAddress(), orderResponse.getAddress())
                && Objects.equals(getPaymentType(), orderResponse.getPaymentType())
                && Objects.equals(getDeliveryType(), orderResponse.getDeliveryType())
                && Objects.equals(getArticle(), orderResponse.getArticle())
                && Objects.equals(getAmount(), orderResponse.getAmount())
                && Objects.equals(getPrice(), orderResponse.getPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId(), getNumber(), getTitle(), getCost()
                , getDate(), getReceiver(), getAddress(), getPaymentType(), getDeliveryType()
                , getArticle(), getAmount(), getPrice());
    }


    @Override
    public String toString() {
        return "OrderResponse{" +
                "id=" + getId() +
                ", number=" + getNumber() +
                ", title=" + getTitle() +
                ", cost=" + getCost() +
                ", date=" + getDate() +
                ", receiver=" + getReceiver() +
                ", address=" + getAddress() +
                ", paymentType=" + getPaymentType() +
                ", deliveryType=" + getDeliveryType() +
                ", article=" + getArticle() +
                ", amount=" + getAmount() +
                ", price=" + getPrice() +
                '}';
    }
}
