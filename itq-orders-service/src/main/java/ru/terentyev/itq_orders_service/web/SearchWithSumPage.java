package ru.terentyev.itq_orders_service.web;

import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.TextField;

import java.time.LocalDate;

public class SearchWithSumPage extends BasePage {

    public SearchWithSumPage(){
        TextField<LocalDate> dateField = new TextField<>("date");
        NumberTextField<Integer> sumField = new NumberTextField<>("sum");
        dateField.setRequired(true);
        sumField.setRequired(true);
        form.add(dateField);
        form.add(sumField);
    }
}
