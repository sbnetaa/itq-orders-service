package ru.terentyev.itq_orders_service.web;

import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.TextField;

import java.time.LocalDate;

public class SearchWithArticlePage extends BasePage {

    public SearchWithArticlePage(){
        super();
        TextField<LocalDate> dateFromField = new TextField<>("dateFrom");
        TextField<LocalDate> dateToField = new TextField<>("dateTo");
        NumberTextField<Integer> articleField = new NumberTextField<>("article");
        dateFromField.setRequired(true);
        dateToField.setRequired(true);
        articleField.setRequired(true);
        form.add(dateFromField);
        form.add(dateToField);
        form.add(articleField);
    }
}
