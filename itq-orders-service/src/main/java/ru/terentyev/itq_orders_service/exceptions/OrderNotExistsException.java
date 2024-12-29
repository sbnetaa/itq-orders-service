package ru.terentyev.itq_orders_service.exceptions;

public class OrderNotExistsException extends RuntimeException {

    public OrderNotExistsException(Long id){
        super("Заказ с ID " + id + " не найден.");
    }
}
