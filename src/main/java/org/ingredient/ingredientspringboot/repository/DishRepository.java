package org.ingredient.ingredientspringboot.repository;



import org.ingredient.ingredientspringboot.dto.DishIngredientUpdateRequest;
import org.ingredient.ingredientspringboot.dto.IngredientInDishResponse;
import org.ingredient.ingredientspringboot.entity.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class DishRepository {

    private final JdbcTemplate jdbcTemplate;

    public DishRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    private final RowMapper<Dish> dishRowMapper = (rs, rowNum) -> {
        Dish dish = new Dish();
        dish.setId(rs.getLong("id"));
        dish.setName(rs.getString("name"));
        dish.setDishType(DishTypeEnum.valueOf(rs.getString("dish_type")));
        dish.setSellingPrice(rs.getObject("selling_price") != null ? rs.getDouble("selling_price") : null);
        return dish;
    };


    private final RowMapper<DishIngredient> dishIngredientRowMapper = (rs, rowNum) -> {
        DishIngredient di = new DishIngredient();
        di.setIngredientId(rs.getInt("ingredient_id"));
        di.setRequiredQuantity(rs.getDouble("required_quantity"));
        di.setUnit(org.ingredient.ingredientspringboot.entity.UnitEnum.valueOf(rs.getString("unit")));


        IngredientInDishResponse ingredientInfo = new IngredientInDishResponse();
        ingredientInfo.setId(rs.getInt("ingredient_id"));
        ingredientInfo.setName(rs.getString("ingredient_name"));
        ingredientInfo.setCategory(CategoryEnum.valueOf(rs.getString("category")));
        ingredientInfo.setPrice(rs.getDouble("ingredient_price"));
        return di;
    };

    public List<Dish> findAll() {
        String dishSql = """
            SELECT id, name, dish_type, selling_price 
            FROM dish 
            ORDER BY name ASC
            """;

        List<Dish> dishes = jdbcTemplate.query(dishSql, dishRowMapper);


        String ingredientsSql = """
            SELECT di.id_dish, 
                   di.id_ingredient,
                   di.required_quantity,
                   di.unit,
                   i.name as ingredient_name,
                   i.category,
                   i.price as ingredient_price
            FROM dish_ingredient di
            JOIN ingredient i ON di.id_ingredient = i.id
            """;

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(ingredientsSql);


        Map<Long, List<IngredientInDishResponse>> ingredientsByDish = new HashMap<>();

        for (Map<String, Object> row : rows) {
            Long dishId = ((Number) row.get("id_dish")).longValue();

            IngredientInDishResponse ing = new IngredientInDishResponse();
            ing.setId(((Number) row.get("id_ingredient")).intValue());
            ing.setName((String) row.get("ingredient_name"));
            ing.setCategory(CategoryEnum.valueOf((String) row.get("category")));
            ing.setPrice(((Number) row.get("ingredient_price")).doubleValue());

            ingredientsByDish.computeIfAbsent(dishId, k -> new ArrayList<>()).add(ing);
        }

        for (Dish dish : dishes) {
            dish.setDishIngredients(new ArrayList<>());
        }

        return dishes;
    }

    public void deleteAllIngredientsByDishId(Long dishId) {
        String sql = "DELETE FROM dish_ingredient WHERE id_dish = ?";
        jdbcTemplate.update(sql, dishId);
    }

    public void attachIngredient(Long dishId, DishIngredientUpdateRequest req) {
        String checkSql = "SELECT COUNT(*) FROM ingredient WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(checkSql, Integer.class, req.getIngredientId());

        if (count == null || count == 0) {
            return;
        }

        String insertSql = """
        INSERT INTO dish_ingredient (id_dish, id_ingredient, required_quantity, unit)
        VALUES (?, ?, ?, ?::unit)
        """;

        jdbcTemplate.update(insertSql,
                dishId,
                req.getIngredientId(),
                req.getQuantity(),
                req.getUnit().name());
    }
}
