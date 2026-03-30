package org.ingredient.ingredientspringboot.service;

import org.ingredient.ingredientspringboot.entity.Ingredient;
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
}