//package ru.terentyev.itq_orders_service.utils;
//
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.factory.Mappers;
//import org.springframework.stereotype.Component;
//import ru.terentyev.itq_orders_service.entities.Order;
//import ru.terentyev.itq_orders_service.entities.OrderDetails;
//import ru.terentyev.itq_orders_service.entities.OrderRequest;
//import ru.terentyev.itq_orders_service.entities.OrderResponse;
//import ru.terentyev.itq_orders_service.repositories.ProductRepository;
//
//@Mapper
//@Component
//public interface OrderMapper {
//    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);
//    ProductRepository productRepository = new ProductRepository();
//
//    @Mapping(target = "cost"
//            , expression = "java(takeCost(orderRequest.getArticle(), orderRequest.getAmount()))")
//    @Mapping(target = "date", expression = "java(java.time.LocalDate.now())")
//    Order toOrder(OrderRequest orderRequest);
//
//    @Mapping(target = "orderId", source = "order.id")
//    OrderDetails toOrderDetails(Order order, OrderRequest orderRequest);
//
//    OrderResponse toOrderResponse(Order order, OrderDetails orderDetails);
//
//    default Integer takeCost(Integer article, Integer amount) {
//        return productRepository.findByArticle(article).getPrice() * amount;
//    }
//}
