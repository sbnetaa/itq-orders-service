package ru.terentyev.itq_orders_service.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.terentyev.itq_orders_service.entities.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class ProductRepository extends AbstractRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Product findByArticle(Integer article) {
        String sql = "SELECT * FROM products WHERE article = ?";
        return jdbcTemplate.queryForObject(sql, new ProductRowMapper(), article);
    }


    private static class ProductRowMapper implements RowMapper<Product> {
        @Override
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            Product product = new Product();
            product.setId(rs.getLong("id"));
            product.setArticle(rs.getInt("article"));
            product.setPrice(rs.getInt("price"));
            product.setTitle(rs.getString("title"));
            return product;
        }
    }
}
