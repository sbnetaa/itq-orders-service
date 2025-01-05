package ru.terentyev.itq_orders_service.web;

import org.apache.wicket.feedback.IFeedbackMessageFilter;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.util.tester.FormTester;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class FindByIdPageTest extends BasePageTest {

    @Test
    public void testPageRenders() {
        tester.startPage(FindByIdPage.class);
        tester.assertRenderedPage(FindByIdPage.class);
        tester.assertComponent("form:id", NumberTextField.class);
    }


    @Test
    public void testFieldsRequired() {
        tester.startPage(FindByIdPage.class);
        FormTester formTester = tester.newFormTester("form");
        tester.assertRequired("form:id");
        formTester.submit("submitButton");
        tester.assertComponentOnAjaxResponse("form:feedbackPanel");
        tester.assertErrorMessages("Поле 'id' обязательно для ввода.");
    }

    @Test
    public void testFormSubmissionSuccess() {
        tester.startPage(FindByIdPage.class);
        FormTester formTester = tester.newFormTester("form");
        formTester.setValue("id", "2");
        formTester.submit("submitButton");
        tester.assertNoErrorMessage();
        tester.assertComponentOnAjaxResponse("form:feedbackPanel");
        tester.assertFeedbackMessages(IFeedbackMessageFilter.ALL, "Поиск выполнен");
        verify(orderService, times(1)).takeSingleOrder(any());
    }
}
