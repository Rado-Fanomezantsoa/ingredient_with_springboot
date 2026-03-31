package org.ingredient.ingredientspringboot.service;

import org.ingredient.ingredientspringboot.dto.DishIngredientUpdateRequest;
import org.ingredient.ingredientspringboot.dto.DishResponse;
import org.ingredient.ingredientspringboot.dto.IngredientInDishResponse;
import org.ingredient.ingredientspringboot.entity.Dish;
import org.ingredient.ingredientspringboot.exception.DishNotFoundException;
import org.ingredient.ingredientspringboot.repository.DishRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishService {

    private final DishRepository repository;

    public DishService(DishRepository repository) {
        this.repository = repository;
    }

    public List<DishResponse> getAllDishes() {
        List<Dish> dishes = repository.findAll();

        return dishes.stream().map(dish -> {
            DishResponse response = new DishResponse();
            response.setId(dish.getId());
            response.setName(dish.getName());
            response.setDishType(dish.getDishType());
            response.setSellingPrice(dish.getSellingPrice());


            response.setIngredients(new ArrayList<>());

            return response;
        }).collect(Collectors.toList());
    }
    public void updateDishIngredients(Long dishId, List<DishIngredientUpdateRequest> newIngredients) {

        if (!dishExists(dishId)) {
            throw new DishNotFoundException("Dish.id=" + dishId + " is not found");
        }
        repository.deleteAllIngredientsByDishId(dishId);
        if (newIngredients != null) {
            for (DishIngredientUpdateRequest req : newIngredients) {
                repository.attachIngredient(dishId, req);
            }
        }
    }
    private boolean dishExists(Long dishId) {
        String sql = "SELECT COUNT(*) FROM dish WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, dishId);
        return count != null && count > 0;
    }
}
