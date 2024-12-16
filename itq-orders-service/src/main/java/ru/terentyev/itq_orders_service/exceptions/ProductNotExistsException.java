package ru.terentyev.itq_orders_service.exceptions;

public class ProductNotExistsException extends RuntimeException {

    public ProductNotExistsException(Integer article) {
        super("Товар с артикулом " + article + " не найден.");
    }
}
