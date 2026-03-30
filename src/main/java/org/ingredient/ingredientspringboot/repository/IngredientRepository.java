package org.ingredient.ingredientspringboot.repository;

import org.ingredient.ingredientspringboot.entity.CategoryEnum;
import org.ingredient.ingredientspringboot.entity.Ingredient;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class IngredientRepository {

    private final JdbcTemplate jdbcTemplate;

    public IngredientRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Ingredient> ingredientRowMapper = (rs, rowNum) -> {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(rs.getInt("id"));
        ingredient.setName(rs.getString("name"));
        ingredient.setPrice(rs.getDouble("price"));
        ingredient.setCategory(CategoryEnum.valueOf(rs.getString("category")));
        ingredient.setInitialStock(rs.getDouble("initial_stock"));
        return ingredient;
    };

    public List<Ingredient> findAll() {
        String sql = """
            SELECT id, name, price, category, initial_stock 
            FROM ingredient 
            ORDER BY name ASC
            """;
        return jdbcTemplate.query(sql, ingredientRowMapper);
    }
}