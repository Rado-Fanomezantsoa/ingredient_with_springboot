package org.ingredient.ingredientspringboot.service;

import org.ingredient.ingredientspringboot.entity.Ingredient;
import org.ingredient.ingredientspringboot.exception.IngredientNotFoundException;
import org.ingredient.ingredientspringboot.repository.IngredientRepository;
import org.springframework.stereotype.Service;

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
}