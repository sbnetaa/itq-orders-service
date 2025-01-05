package ru.terentyev.itq_orders_service.web;

import org.apache.wicket.feedback.IFeedbackMessageFilter;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.util.tester.FormTester;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CreateOrderPageTest extends BasePageTest {

    @Test
    public void testCreateOrderPageRendersSuccessfully() {
        tester.startPage(CreateOrderPage.class);
        tester.assertRenderedPage(CreateOrderPage.class);
        tester.assertComponent("form:article", NumberTextField.class);
        tester.assertComponent("form:amount", NumberTextField.class);
        tester.assertComponent("form:receiver", TextField.class);
        tester.assertComponent("form:address", TextField.class);
        tester.assertComponent("form:paymentType", TextField.class);
        tester.assertComponent("form:deliveryType", TextField.class);
    }

    @Test
    public void testCreateOrderFormValidationSuccess() {
        tester.startPage(new CreateOrderPage());

        FormTester formTester = tester.newFormTester("form");
        formTester.setValue("article", "131");
        formTester.setValue("amount", "3");
        formTester.setValue("receiver", "Дмитрий");
        formTester.setValue("address", "Москва, ул. Пржевальского");
        formTester.setValue("paymentType", "Картой");
        formTester.setValue("deliveryType", "Самовывоз");
        formTester.submit("submitButton");
        tester.assertNoErrorMessage();
        tester.assertComponentOnAjaxResponse("form:feedbackPanel");
        tester.assertFeedbackMessages(IFeedbackMessageFilter.ALL, "Заказ создан");
        verify(orderService, times(1)).createOrder(any());
    }

    @Test
    public void testCreateOrderFormValidationFailure() {
        tester.startPage(new CreateOrderPage());
        FormTester formTester = tester.newFormTester("form");
        formTester.setValue("amount", "-1");
        formTester.submit("submitButton");
        tester.assertRequired("form:article");
        tester.assertRequired("form:amount");
        tester.assertRequired("form:receiver");
        tester.assertRequired("form:paymentType");
        tester.assertRequired("form:deliveryType");
        tester.assertRequired("form:address");
        tester.assertErrorMessages(
                "Поле 'article' обязательно для ввода.",
                "Поле 'receiver' обязательно для ввода.",
                "Поле 'address' обязательно для ввода.",
                "Поле 'paymentType' обязательно для ввода.",
                "Поле 'deliveryType' обязательно для ввода.",
                "'amount' не находится между 1 и 2147483647."
        );
        tester.assertComponentOnAjaxResponse("form:feedbackPanel");
    }
}