package ru.terentyev.itq_orders_service.web;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.IFeedbackMessageFilter;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import ru.terentyev.itq_orders_service.entities.wicketmodels.RequestModel;
import ru.terentyev.itq_orders_service.schemas.OrderRequestSchema;
import ru.terentyev.itq_orders_service.services.OrderService;

public abstract class BasePage extends WebPage {

    @SpringBean
    protected OrderService orderService;
    protected Form<OrderRequestSchema> form;
    protected AjaxButton submitButton;
    protected IModel<OrderRequestSchema> requestModel;
    protected Panel resultsPanel;
    protected OrderResponseDataProvider resultsProvider;
    protected FeedbackPanel feedbackPanel;
    protected String successMessage;
    protected Link<Void> mainPageLink;

    public BasePage(){
        successMessage = "Поиск выполнен";
        requestModel = new CompoundPropertyModel<>(new RequestModel());
        form = new Form<>("form", requestModel);
        resultsProvider = new OrderResponseDataProvider(orderService);
        add(resultsPanel = new ResultsPanel("resultsPanel", resultsProvider));
        form.add(feedbackPanel = new FeedbackPanel("feedbackPanel", (IFeedbackMessageFilter) feedbackMessage ->
                feedbackMessage.getLevel() == FeedbackMessage.ERROR
                || feedbackMessage.getLevel() == FeedbackMessage.SUCCESS));
        resultsPanel.setOutputMarkupId(true);
        feedbackPanel.setOutputMarkupId(true);
        submitButton = new AjaxButton("submitButton", form) {
            @Override
            public void onSubmit(AjaxRequestTarget target){
                try {
                    resultsProvider.updateData(requestModel.getObject());
                    feedbackPanel.success(successMessage);
                    target.add(resultsPanel);
                } catch (Exception e) {
                    feedbackPanel.error(e.getMessage());
//                    e.printStackTrace();
                }
                target.add(feedbackPanel);
            }

            @Override
            public void onError(AjaxRequestTarget target) {
                target.add(feedbackPanel);
            }
        };
        mainPageLink = new Link<>("mainPageLink") {
            @Override
            public void onClick() {
                setResponsePage(HomePage.class);
            }
        };
        add(mainPageLink);
        submitButton.setOutputMarkupId(true);
        form.add(submitButton);
        add(form);
    }
}
