package org.ingredient.ingredientspringboot.dto;

import lombok.Data;
import org.ingredient.ingredientspringboot.entity.DishTypeEnum;

import java.util.List;

@Data
public class DishResponse {
    private Long id;
    private String name;
    private DishTypeEnum dishType;
    private Double sellingPrice;
    private List<IngredientInDishResponse> ingredients;
}
