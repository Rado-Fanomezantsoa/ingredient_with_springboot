package org.ingredient.ingredientspringboot.service;

import org.ingredient.ingredientspringboot.dto.StockResponse;
import org.ingredient.ingredientspringboot.entity.Ingredient;
import org.ingredient.ingredientspringboot.exception.IngredientNotFoundException;
import org.ingredient.ingredientspringboot.repository.IngredientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class IngredientService {

    private final IngredientRepository repository;

    public IngredientService(IngredientRepository repository) {
        this.repository = repository;
    }

    public List<Ingredient> getAllIngredients() {
        return repository.findAll();
    }

    public Ingredient getById(Integer id) {
        Ingredient ingredient = repository.findById(id);

        if (ingredient == null) {
            throw new IngredientNotFoundException("Ingredient.id=" + id + " is not found");
        }

        return ingredient;
    }

    public StockResponse getStockAt(Integer id, LocalDateTime at, String unit) {
        Ingredient ingredient = repository.findById(id);
        if (ingredient == null) {
            throw new IngredientNotFoundException("Ingredient.id=" + id + " is not found");
        }

        Double stockValue = repository.getStockValueAt(id, at);

        StockResponse response = new StockResponse();
        response.setUnit(unit.toUpperCase());
        response.setValue(stockValue);
        return response;
    }
}