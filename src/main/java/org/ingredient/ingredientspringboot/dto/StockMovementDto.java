package org.ingredient.ingredientspringboot.dto;

import org.ingredient.ingredientspringboot.entity.MovementType;
import org.ingredient.ingredientspringboot.entity.StockMovement;
import org.ingredient.ingredientspringboot.entity.UnitEnum;

import java.time.Instant;

public class StockMovementDto {
    private Long id;
    private Instant createdAt;
    private UnitEnum unit;
    private double value;
    private MovementType type;

    public StockMovementDto(StockMovement sm) {
        this.id = sm.getId();
        this.createdAt = sm.getCreatedAt();
        this.unit = sm.getUnit();
        this.value = sm.getValue();
        this.type = sm.getType();
    }

    public Long getId() { return id; }
    public Instant getCreatedAt() { return createdAt; }
    public UnitEnum getUnit() { return unit; }
    public double getValue() { return value; }
    public MovementType getType() { return type; }
}
