package ru.terentyev.itq_orders_service.entities;

import java.util.Objects;

public class OrderDetails extends AbstractEntity {

    private Integer article;
    private String title;
    private Integer amount;
    private Integer price;
    private Long orderId;

    public Integer getArticle() {
        return article;
    }

    public void setArticle(Integer article) {
        this.article = article;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        OrderDetails that = (OrderDetails) o;
        return Objects.equals(article, that.article) && Objects.equals(title, that.title) && Objects.equals(amount, that.amount) && Objects.equals(price, that.price) && Objects.equals(orderId, that.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), article, title, amount, price, orderId);
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "article=" + article +
                ", title='" + title + '\'' +
                ", amount=" + amount +
                ", price=" + price +
                ", orderId=" + orderId +
                '}';
    }
}
