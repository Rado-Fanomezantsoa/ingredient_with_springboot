package org.ingredient.ingredientspringboot.dto;

import lombok.Data;
import org.ingredient.ingredientspringboot.entity.MovementType;

import java.time.Instant;

@Data
public class StockMovementResponse {
    private Integer id;
    private Instant creationDatetime;
    private String unit;
    private Double quantity;
    private MovementType type;
}
