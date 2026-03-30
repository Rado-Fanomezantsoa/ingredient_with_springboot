package org.ingredient.ingredientspringboot.dto;

import lombok.*;
import org.ingredient.ingredientspringboot.entity.CategoryEnum;


import lombok.Data;

@Data
public class IngredientResponse {
    private int id;
    private String name;
    private Double price;
    private CategoryEnum category;
    private Double initialStock;
}
