package ru.terentyev.itq_orders_service.repositories;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.terentyev.itq_orders_service.entities.OrderDetails;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class OrderDetailsRepository extends AbstractRepository {

    private final JdbcTemplate jdbcTemplate;

    public OrderDetailsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public OrderDetails save(OrderDetails orderDetails){
        String sql = "INSERT INTO order_details (article, title, amount, price, order_id) VALUES (?, ?, ?, ?, ?) RETURNING id";
        Long generatedId = jdbcTemplate.queryForObject(sql, Long.class
                , orderDetails.getArticle(), orderDetails.getTitle()
                , orderDetails.getAmount(), orderDetails.getPrice()
                , orderDetails.getOrderId());
        orderDetails.setId(generatedId);
        return orderDetails;
    }

    public OrderDetails findByOrderId(Long id) {
        String sql = "SELECT * FROM order_details WHERE order_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new OrderDetailsRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public static class OrderDetailsRowMapper implements RowMapper<OrderDetails> {

        @Override
        public OrderDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
            OrderDetails details = new OrderDetails();
            details.setId(rs.getLong(1));
            details.setArticle(rs.getInt("article"));
            details.setOrderId(rs.getLong("order_id"));
            details.setPrice(rs.getInt("price"));
            details.setAmount(rs.getInt("amount"));
            details.setTitle(rs.getString("title"));
            return details;
        }
    }
}
