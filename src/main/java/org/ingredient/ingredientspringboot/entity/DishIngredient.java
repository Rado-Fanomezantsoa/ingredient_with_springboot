package org.ingredient.ingredientspringboot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishIngredient {

    private Long id;
    private Long dishId;                    // id_dish dans la BDD
    private Integer ingredientId;           // id_ingredient dans la BDD
    private Double requiredQuantity;        // required_quantity
    private UnitEnum unit;                  // unit (enum)
}