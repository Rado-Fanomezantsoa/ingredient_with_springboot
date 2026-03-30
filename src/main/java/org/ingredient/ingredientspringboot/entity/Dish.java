package org.ingredient.ingredientspringboot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dish {

    private Long id;
    private String name;
    private DishTypeEnum dishType;        
    private Double sellingPrice;


    private List<DishIngredient> dishIngredients = new ArrayList<>();
}