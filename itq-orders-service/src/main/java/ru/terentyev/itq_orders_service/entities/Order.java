package ru.terentyev.itq_orders_service.entities;

import java.time.LocalDate;
import java.util.Objects;

public class Order extends AbstractEntity{

    private String number;
    private Integer cost;
    private LocalDate date;
    private String receiver;
    private String address;
    private String paymentType;
    private String deliveryType;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Order order = (Order) o;
        return Objects.equals(number, order.number) && Objects.equals(cost, order.cost) && Objects.equals(date, order.date) && Objects.equals(receiver, order.receiver) && Objects.equals(address, order.address) && Objects.equals(paymentType, order.paymentType) && Objects.equals(deliveryType, order.deliveryType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), number, cost, date, receiver, address, paymentType, deliveryType);
    }
}
