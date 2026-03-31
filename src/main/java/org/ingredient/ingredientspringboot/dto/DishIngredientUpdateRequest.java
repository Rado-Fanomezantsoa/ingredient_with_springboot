package org.ingredient.ingredientspringboot.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.ingredient.ingredientspringboot.entity.UnitEnum;

@Data
public class DishIngredientUpdateRequest {

    @NotNull(message = "ingredientId est obligatoire")
    private Integer ingredientId;

    @NotNull(message = "quantity est obligatoire")
    @Positive(message = "La quantité doit être positive")
    private Double quantity;

    @NotNull(message = "unit est obligatoire")
    private UnitEnum unit;
}
