package org.ingredient.ingredientspringboot.mapper;

import org.ingredient.ingredientspringboot.dto.IngredientResponse;
import org.ingredient.ingredientspringboot.entity.Ingredient;
import org.springframework.stereotype.Component;

@Component
public class IngredientMapper {

    public IngredientResponse toResponse(Ingredient ingredient) {
        IngredientResponse r = new IngredientResponse();
        r.setId(ingredient.getId());
        r.setName(ingredient.getName());
        r.setPrice(ingredient.getPrice());
        r.setCategory(ingredient.getCategory());
        r.setInitialStock(ingredient.getInitialStock());
        return r;
    }
}