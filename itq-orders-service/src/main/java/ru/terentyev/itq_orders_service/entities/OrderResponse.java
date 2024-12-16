package ru.terentyev.itq_orders_service.entities;

import java.time.LocalDate;
import java.util.Objects;

public class OrderResponse extends AbstractEntity {

    private String number;
    private String title;
    private Integer cost;
    private LocalDate date;
    private String receiver;
    private String address;
    private String paymentType;
    private String deliveryType;
    private Integer article;
    private Integer amount;
    private Integer price;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Integer getArticle() {
        return article;
    }

    public void setArticle(Integer article) {
        this.article = article;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        OrderResponse response = (OrderResponse) o;
        return Objects.equals(number, response.number) && Objects.equals(title, response.title) && Objects.equals(cost, response.cost) && Objects.equals(date, response.date) && Objects.equals(receiver, response.receiver) && Objects.equals(address, response.address) && Objects.equals(paymentType, response.paymentType) && Objects.equals(deliveryType, response.deliveryType) && Objects.equals(article, response.article) && Objects.equals(amount, response.amount) && Objects.equals(price, response.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), number, title, cost, date, receiver, address, paymentType, deliveryType, article, amount, price);
    }

    @Override
    public String toString() {
        return "OrderResponse{" +
                "number='" + number + '\'' +
                ", title='" + title + '\'' +
                ", cost=" + cost +
                ", date=" + date +
                ", receiver='" + receiver + '\'' +
                ", address='" + address + '\'' +
                ", paymentType='" + paymentType + '\'' +
                ", deliveryType='" + deliveryType + '\'' +
                ", article=" + article +
                ", amount=" + amount +
                ", price=" + price +
                '}';
    }
}
