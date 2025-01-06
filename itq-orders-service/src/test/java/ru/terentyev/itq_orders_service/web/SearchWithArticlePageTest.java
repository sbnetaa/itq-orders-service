package ru.terentyev.itq_orders_service.web;

import org.apache.wicket.feedback.IFeedbackMessageFilter;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.util.tester.FormTester;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class SearchWithArticlePageTest extends BasePageTest {

    @Test
    public void testPageRenders() {
        tester.startPage(SearchWithArticlePage.class);
        tester.assertRenderedPage(SearchWithArticlePage.class);
        tester.assertComponent("form:dateFrom", TextField.class);
        tester.assertComponent("form:dateTo", TextField.class);
        tester.assertComponent("form:article", NumberTextField.class);
    }

    @Test
    public void testFieldsRequired() {
        tester.startPage(SearchWithArticlePage.class);
        FormTester formTester = tester.newFormTester("form");
        tester.assertRequired("form:dateFrom");
        tester.assertRequired("form:dateTo");
        tester.assertRequired("form:article");
        formTester.submit("submitButton");
        tester.assertComponentOnAjaxResponse("form:feedbackPanel");
        tester.assertErrorMessages("Поле 'dateFrom' обязательно для ввода.",
                "Поле 'dateTo' обязательно для ввода.",
                "Поле 'article' обязательно для ввода.");
    }

    @Test
    public void testFormSubmissionSuccess() {
        tester.startPage(SearchWithArticlePage.class);
        FormTester formTester = tester.newFormTester("form");
        formTester.setValue("dateFrom", "05.12.2024");
        formTester.setValue("dateTo", "05.01.2025");
        formTester.setValue("article", "43");
        formTester.submit("submitButton");
        tester.assertNoErrorMessage();
        tester.assertComponentOnAjaxResponse("form:feedbackPanel");
        tester.assertFeedbackMessages(IFeedbackMessageFilter.ALL, "Поиск выполнен");
        verify(orderService, times(1)).search(any());
    }
}
