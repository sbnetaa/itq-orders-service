package ru.terentyev.itq_orders_service.web;

import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.cycle.RequestCycle;
import ru.terentyev.itq_orders_service.entities.OrderResponseSchemaWrapper;
import ru.terentyev.itq_orders_service.schemas.OrderRequestSchema;
import ru.terentyev.itq_orders_service.services.OrderService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class OrderResponseDataProvider implements IDataProvider<OrderResponseSchemaWrapper> {

    private List<OrderResponseSchemaWrapper> responsesList;
    private OrderService orderService;

    public OrderResponseDataProvider(OrderService orderService) {
        this.responsesList = new ArrayList<>();
        this.orderService = orderService;
    }

    public void updateData(OrderRequestSchema request, Long idToSearch){
        responsesList.clear();

        if (idToSearch != null) {
            responsesList.add(new OrderResponseSchemaWrapper(orderService.takeSingleOrder(idToSearch)));
        }

        if (request == null) {
            return;
        }

        if ((request.getSum() != null && request.getDate() != null)
                || (request.getArticle() != null && request.getDateFrom() != null && request.getDateTo() != null)) {
            orderService.search(request).forEach(or -> responsesList.add(new OrderResponseSchemaWrapper(or)));
        } else {
            responsesList.add(new OrderResponseSchemaWrapper(orderService.createOrder(request)));
        }
    }
    @Override
    public Iterator<? extends OrderResponseSchemaWrapper> iterator(long first, long count) {
        return responsesList.subList((int) first, Math.min((int) (first + count), responsesList.size())).iterator();
//        return responsesList.subList((int) first, (int) Math.min(first + count, responsesList.size())).iterator();
    }

    @Override
    public long size() {
        return responsesList.size();
    }

    public void setResponsesList(List<OrderResponseSchemaWrapper> responsesList){
        this.responsesList = responsesList;
    }

    @Override
    public IModel<OrderResponseSchemaWrapper> model(OrderResponseSchemaWrapper orderResponseSchemaWrapper) {
        return new Model<>(orderResponseSchemaWrapper);
    }

    @Override
    public void detach(){
//        super.detach();
        responsesList.clear();
    }
}
