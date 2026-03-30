package org.ingredient.ingredientspringboot.mapper;

import org.ingredient.ingredientspringboot.dto.IngredientResponse;
import org.ingredient.ingredientspringboot.entity.Ingredient;
import org.springframework.stereotype.Component;

@Component
public class IngredientMapper {

    public IngredientResponse toResponse(Ingredient ingredient) {
        if (ingredient == null) return null;

        IngredientResponse response = new IngredientResponse();
        response.setId(ingredient.getId());
        response.setName(ingredient.getName());
        response.setPrice(ingredient.getPrice());
        response.setCategory(ingredient.getCategory());
        response.setInitialStock(ingredient.getInitialStock());
        return response;
    }
}