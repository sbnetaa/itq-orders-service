package ru.terentyev.itq_orders_service.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.terentyev.itq_orders_service.entities.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class ProductRepository extends AbstractRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ProductRepository(){}

    public Product findByArticle(Integer article) {
        String sql = "SELECT * FROM products WHERE article = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new ProductRowMapper(), article);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
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
