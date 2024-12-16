package ru.terentyev.itq_orders_service.entities;

import java.time.LocalDate;
import java.util.Objects;

public class OrderRequest extends AbstractEntity{

    private Integer article;
    private Integer amount;
    private String receiver;
    private String address;
    private String paymentType;
    private String deliveryType;
    private Integer sum;
    private LocalDate date;
    private LocalDate dateFrom;
    private LocalDate dateTo;

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

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        OrderRequest that = (OrderRequest) o;
        return Objects.equals(article, that.article) && Objects.equals(amount, that.amount) && Objects.equals(receiver, that.receiver) && Objects.equals(address, that.address) && Objects.equals(paymentType, that.paymentType) && Objects.equals(deliveryType, that.deliveryType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), article, amount, receiver, address, paymentType, deliveryType);
    }

    @Override
    public String toString() {
        return "OrderRequest{" +
                "article=" + article +
                ", amount=" + amount +
                ", receiver='" + receiver + '\'' +
                ", address='" + address + '\'' +
                ", paymentType='" + paymentType + '\'' +
                ", deliveryType='" + deliveryType + '\'' +
                '}';
    }
}
