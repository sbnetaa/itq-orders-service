package ru.terentyev.itq_orders_service.web;

import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.validation.validator.RangeValidator;

public class CreateOrderPage extends BasePage {

    public CreateOrderPage() {
        super();
        successMessage = "Заказ создан";
        NumberTextField<Integer> articleField = new NumberTextField<>("article");
        NumberTextField<Integer> amountField = new NumberTextField<>("amount");
        TextField<String> receiverField = new TextField<>("receiver");
        TextField<String> addressField = new TextField<>("address");
        TextField<String> paymentTypeField = new TextField<>("paymentType");
        TextField<String> deliveryTypeField = new TextField<>("deliveryType");
        articleField.setRequired(true);
//        articleField.add(new ArticleValidator());
        amountField.setRequired(true);
        amountField.add(new RangeValidator<>(1, Integer.MAX_VALUE));
        receiverField.setRequired(true);
        addressField.setRequired(true);
        paymentTypeField.setRequired(true);
        deliveryTypeField.setRequired(true);
        form.add(articleField);
        form.add(amountField);
        form.add(receiverField);
        form.add(addressField);
        form.add(paymentTypeField);
        form.add(deliveryTypeField);
    }
}
