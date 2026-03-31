package org.ingredient.ingredientspringboot.dto;

import lombok.Data;
import org.ingredient.ingredientspringboot.entity.CategoryEnum;

@Data
public class IngredientInDishResponse {
    private Integer id;
    private String name;
    private CategoryEnum category;
    private Double price;
}
