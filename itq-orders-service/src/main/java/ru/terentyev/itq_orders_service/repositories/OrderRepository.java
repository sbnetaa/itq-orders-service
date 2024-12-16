package ru.terentyev.itq_orders_service.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.terentyev.itq_orders_service.entities.Order;
import ru.terentyev.itq_orders_service.entities.OrderDetails;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

@Repository
public class OrderRepository extends AbstractRepository {
    static Map<Order, OrderDetails> orderMap = new LinkedHashMap<>();

    private final JdbcTemplate jdbcTemplate;

    public OrderRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Order save(Order order){
        String sql = "INSERT INTO orders (number, cost, date, receiver, address, payment_type, delivery_type) VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id";
        Long generatedId = jdbcTemplate.queryForObject(sql, Long.class, order.getNumber()
                , order.getCost(), order.getDate(), order.getReceiver()
                , order.getAddress(), order.getPaymentType(), order.getDeliveryType());
        order.setId(generatedId);
        return order;
    }

    public Order findById(Long id) {
        String sql = "SELECT * FROM orders WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new OrderRowMapper(), id);
    }

    public Map<Order, OrderDetails> searchBySumAndDate(Integer minSum, LocalDate atDate) {
        orderMap.clear();
        String sql = "SELECT o.id AS oid, o.*, od.* FROM orders o FULL JOIN order_details od ON" +
                " o.id = od.order_id WHERE o.cost > ? AND o.date = ?"; //
        jdbcTemplate.query(sql, new MixedRowMapper(), minSum, Date.valueOf(atDate)); //
        return orderMap;
    }

    public Map<Order, OrderDetails> searchByArticleMissingAndDate(Integer article, LocalDate dateFrom, LocalDate dateTo) {
        orderMap.clear();
        String sql = "SELECT o.id AS oid, o.*, od.* FROM orders o FULL JOIN order_details od ON" +
                " o.id = od.order_id WHERE od.article != ? AND o.date BETWEEN ? AND ?";
        jdbcTemplate.query(sql, new MixedRowMapper(), article, dateFrom, dateTo);
        return orderMap;
    }

    static class OrderRowMapper implements RowMapper<Order> {

        @Override
        public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
            Order order = new Order();
            order.setReceiver(rs.getString("receiver"));
            order.setDate(rs.getDate("date").toLocalDate());
            order.setCost(rs.getInt("cost"));
            order.setAddress(rs.getString("address"));
            order.setPaymentType(rs.getString("payment_type"));
            order.setDeliveryType(rs.getString("delivery_type"));
            order.setNumber(rs.getString("number"));
            order.setId(rs.getLong(1));
            return order;
        }
    }

    private static class MixedRowMapper implements RowMapper<Void> {

        private final OrderRowMapper orderRowMapper = new OrderRowMapper();
        private final OrderDetailsRepository.OrderDetailsRowMapper detailsRowMapper = new OrderDetailsRepository.OrderDetailsRowMapper();

        @Override
        public Void mapRow(ResultSet rs, int rowNum) throws SQLException {
            Order order = orderRowMapper.mapRow(rs, rowNum);
            OrderDetails orderDetails = detailsRowMapper.mapRow(rs, rowNum);
            orderMap.put(order, orderDetails);
            return null;
        }
    }
}
