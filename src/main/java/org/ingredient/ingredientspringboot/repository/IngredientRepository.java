package org.ingredient.ingredientspringboot.repository;

import org.ingredient.ingredientspringboot.dto.StockMovementResponse;
import org.ingredient.ingredientspringboot.entity.CategoryEnum;
import org.ingredient.ingredientspringboot.entity.Ingredient;
import org.ingredient.ingredientspringboot.entity.MovementType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
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

    public Ingredient findById(Integer id) {
        String sql = """
        SELECT id, name, price, category, initial_stock 
        FROM ingredient 
        WHERE id = ?
        """;

        try {
            return jdbcTemplate.queryForObject(sql, ingredientRowMapper, id);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Double getStockValueAt(Integer ingredientId, LocalDateTime at) {
        String sql = """
        SELECT COALESCE(
            SUM(CASE WHEN type = 'IN' THEN quantity ELSE -quantity END), 
            0
        ) as stock_value
        FROM stock_movement 
        WHERE id_ingredient = ? 
          AND creation_datetime <= ?
        """;

        return jdbcTemplate.queryForObject(sql, Double.class, ingredientId, at);
    }

    public List<StockMovementResponse> findStockMovementsByIngredientId(
            Integer ingredientId, Instant from, Instant to) {

        String sql = """
        SELECT id, 
               creation_datetime, 
               unit, 
               quantity, 
               type
        FROM stock_movement 
        WHERE id_ingredient = ?
          AND (? IS NULL OR creation_datetime >= ?)
          AND (? IS NULL OR creation_datetime <= ?)
        ORDER BY creation_datetime DESC
        """;

        return jdbcTemplate.query(sql,
                (rs, rowNum) -> {
                    StockMovementResponse m = new StockMovementResponse();
                    m.setId(rs.getInt("id"));
                    m.setCreationDatetime(rs.getTimestamp("creation_datetime").toInstant());
                    m.setUnit(rs.getString("unit"));
                    m.setQuantity(rs.getDouble("quantity"));
                    m.setType(MovementType.valueOf(rs.getString("type")));
                    return m;
                },
                ingredientId,
                from, from,
                to, to
        );
    }
}