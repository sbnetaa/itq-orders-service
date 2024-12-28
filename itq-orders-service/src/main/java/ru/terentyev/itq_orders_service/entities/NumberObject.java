package ru.terentyev.itq_orders_service.entities;

import java.io.Serializable;

public class NumberObject implements Serializable {

    private String id;
    private String number;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
