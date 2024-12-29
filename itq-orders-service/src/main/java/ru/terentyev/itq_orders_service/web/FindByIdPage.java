package ru.terentyev.itq_orders_service.web;

import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.validation.validator.RangeValidator;

public class FindByIdPage extends BasePage {

    public FindByIdPage(){
        super();
        NumberTextField<Long> idToSearchField = new NumberTextField<>("id");
        idToSearchField.setRequired(true);
        idToSearchField.add(new RangeValidator<Long>(0L, Long.MAX_VALUE));
        form.add(idToSearchField);
    }
}
