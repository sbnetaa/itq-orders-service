package ru.terentyev.itq_orders_service.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

import io.swagger.v3.oas.annotations.tags.Tag;
import ru.terentyev.itq_orders_service.utils.SwaggerExamples;

import java.util.List;

@RequestMapping(value = "/api/v1/orders", produces = MediaType.APPLICATION_JSON_VALUE
        , consumes = MediaType.APPLICATION_JSON_VALUE
        , headers = "Accept=application/json")
@RestController
@Tag(name = "Заказы", description = "Операции, связанные с заказами.")
public class OrderController extends AbstractController implements OrdersApi {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(summary = "Создание заказа" , description = "Создает новый заказ", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(examples =
                    { @ExampleObject(SwaggerExamples.POST_REQUEST_CREATE)})))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Заказ создан и сохранен", content = @Content(mediaType = "application/json",
                    schema = @Schema(example = SwaggerExamples.POST_200_RESPONSE_CREATE))),
            @ApiResponse(responseCode = "400", description = "Некорректно составлен запрос, либо ошибки валидации", content = @Content(mediaType = "application/json",
                    schema = @Schema(example = SwaggerExamples.POST_400_RESPONSE_CREATE)))})
    @Override
    @PostMapping("/create")
    public ResponseEntity<OrderResponseSchema> createOrder(@Parameter(description = "Тело заказа для создания") @RequestBody OrderRequestSchema request){
        return new ResponseEntity<>(orderService.createOrder(request), HttpStatus.CREATED);
    }


    @Operation(summary = "Просмотр заказа" , description = "Отображает информацию об одном заказе по его ID", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(examples =
                    { @ExampleObject(SwaggerExamples.GET_REQUEST_FIND_BY_ID)})))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Заказ найден", content = @Content(mediaType = "application/json",
                    schema = @Schema(example = SwaggerExamples.GET_200_RESPONSE_FIND_BY_ID))),
            @ApiResponse(responseCode = "404", description = "Заказ не найден", content = @Content(mediaType = "application/json",
                    schema = @Schema(example = SwaggerExamples.GET_400_RESPONSE_FIND_BY_ID)))
    })
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseSchema> takeSingleOrder(@Parameter(description = "ID задачи", example = "1") @PathVariable Long id) {
        return new ResponseEntity<>(orderService.takeSingleOrder(id), HttpStatus.OK);
    }

   /*
    * В ТЗ указано "3. Получение заказа за заданную дату и больше заданной общей суммы заказа",
    * но таких заказов может быть несколько, поэтому сделал возврат списка
    */
   @Operation(summary = "Поиск заказов" , description = "Поиск заказов по дате и либо превышающей сумме, либо исключая артикул", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
           content = @Content(examples =
                   { @ExampleObject(name = "Поиск по превышающей сумме и на указанную дату", value = SwaggerExamples.POST_REQUEST_SEARCH_WITH_SUM)
                   , @ExampleObject(name = "Поиск по промежутку времени и исключая артикул", value = SwaggerExamples.POST_REQUEST_SEARCH_WITH_ARTICLE)})))
   @ApiResponses(value = {
           @ApiResponse(responseCode = "201", description = "Поиск выполнен", content = @Content(mediaType = "application/json",
                   schema = @Schema(example = SwaggerExamples.POST_200_RESPONSE_SEARCH))),
           @ApiResponse(responseCode = "400", description = "Некорректно составлен запрос, либо ошибки валидации", content = @Content(mediaType = "application/json",
                   schema = @Schema(example = SwaggerExamples.POST_400_RESPONSE_SEARCH)))})
    @Override
    @PostMapping("/search")
    public ResponseEntity<List<OrderResponseSchema>> searchOrders(@Parameter(description = "Поля заказа для поиска") @RequestBody OrderRequestSchema request) {
        return new ResponseEntity<>(orderService.search(request), HttpStatus.OK);
    }
}
