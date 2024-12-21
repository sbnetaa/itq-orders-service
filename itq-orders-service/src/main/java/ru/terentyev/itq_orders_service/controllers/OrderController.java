package ru.terentyev.itq_orders_service.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.terentyev.itq_orders_service.api.OrdersApi;
import ru.terentyev.itq_orders_service.schemas.OrderRequestSchema;
import ru.terentyev.itq_orders_service.schemas.OrderResponseSchema;
import ru.terentyev.itq_orders_service.services.OrderService;

import java.util.List;

@RequestMapping(value = "/api/v1/orders", produces = MediaType.APPLICATION_JSON_VALUE
        , consumes = MediaType.APPLICATION_JSON_VALUE
        , headers = "Accept=application/json")
@RestController
public class OrderController extends AbstractController implements OrdersApi {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    @PostMapping("/create")
    public ResponseEntity<OrderResponseSchema> createOrder(@RequestBody OrderRequestSchema request){
        return new ResponseEntity<>(orderService.createOrder(request), HttpStatus.CREATED);
    }
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseSchema> takeSingleOrder(@PathVariable Long id) {
        return new ResponseEntity<>(orderService.takeSingleOrder(id), HttpStatus.OK);
    }

   /*
    * В ТЗ указано "3. Получение заказа за заданную дату и больше заданной общей суммы заказа",
    * но таких заказов может быть несколько, поэтому сделал возврат списка
    */
    @Override
    @PostMapping("/search")
    public ResponseEntity<List<OrderResponseSchema>> searchOrders(@RequestBody OrderRequestSchema request) {
        return new ResponseEntity<>(orderService.search(request), HttpStatus.OK);
    }
}
