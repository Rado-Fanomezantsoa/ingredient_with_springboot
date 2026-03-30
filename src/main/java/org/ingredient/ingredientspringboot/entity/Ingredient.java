package org.ingredient.ingredientspringboot.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {

    private int id;
    private String name;
    private Double price;
    private CategoryEnum category;
    private Double initialStock;
}