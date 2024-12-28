package ru.terentyev.itq_orders_service.web;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.CompoundPropertyModel;
import ru.terentyev.itq_orders_service.entities.OrderResponseSchemaWrapper;
import ru.terentyev.itq_orders_service.schemas.OrderResponseSchema;

public class ResultsPanel extends Panel {

    private DataView<OrderResponseSchemaWrapper> dataView;

    public ResultsPanel(String id, OrderResponseDataProvider dataProvider) {
        super(id);
        dataView = new DataView<>("dataView", dataProvider) {

            @Override
            protected void populateItem(Item<OrderResponseSchemaWrapper> item) {
                OrderResponseSchema response = item.getModelObject().getOrderResponseSchema();
                CompoundPropertyModel<OrderResponseSchema> responseModel = new CompoundPropertyModel<>(response);
                item.setDefaultModel(responseModel);
                item.add(new Label("id"));
                item.add(new Label("number"));
                item.add(new Label("title"));
                item.add(new Label("cost"));
                item.add(new Label("date"));
                item.add(new Label("receiver"));
                item.add(new Label("address"));
                item.add(new Label("paymentType"));
                item.add(new Label("deliveryType"));
                item.add(new Label("article"));
                item.add(new Label("amount"));
                item.add(new Label("price"));
            }
        };
        add(dataView);
    }
}
