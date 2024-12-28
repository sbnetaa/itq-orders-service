package ru.terentyev.itq_orders_service.entities.wicketmodels;

import org.apache.wicket.model.LoadableDetachableModel;
import ru.terentyev.itq_orders_service.schemas.OrderRequestSchema;

public class RequestModel extends LoadableDetachableModel<OrderRequestSchema> {


    @Override
    protected OrderRequestSchema load() {
        return new OrderRequestSchema();
    }
}
