package ru.terentyev.itq_orders_service.web;

import org.apache.wicket.feedback.IFeedbackMessageFilter;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.util.tester.FormTester;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class SearchWithSumPageTest extends BasePageTest {

    @Test
    public void testPageRenders() {
        tester.startPage(SearchWithSumPage.class);
        tester.assertRenderedPage(SearchWithSumPage.class);
        tester.assertComponent("form:date", TextField.class);
        tester.assertComponent("form:sum", NumberTextField.class);
    }

    @Test
    public void testFieldsRequired() {
        tester.startPage(SearchWithSumPage.class);
        FormTester formTester = tester.newFormTester("form");
        tester.assertRequired("form:sum");
        tester.assertRequired("form:date");
        formTester.submit("submitButton");
        tester.assertComponentOnAjaxResponse("form:feedbackPanel");
        tester.assertErrorMessages("Поле 'date' обязательно для ввода.",
                "Поле 'sum' обязательно для ввода.");
    }

    @Test
    public void testFormSubmissionSuccess() {
        tester.startPage(SearchWithSumPage.class);
        FormTester formTester = tester.newFormTester("form");
        formTester.setValue("date", "05.01.2025");
        formTester.setValue("sum", "100");
        formTester.submit("submitButton");
        tester.assertNoErrorMessage();
        tester.assertComponentOnAjaxResponse("form:feedbackPanel");
        tester.assertFeedbackMessages(IFeedbackMessageFilter.ALL, "Поиск выполнен");
        verify(orderService, times(1)).search(any());
    }
}
