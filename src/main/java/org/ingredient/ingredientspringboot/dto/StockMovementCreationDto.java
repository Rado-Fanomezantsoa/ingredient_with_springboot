package org.ingredient.ingredientspringboot.dto;

import org.ingredient.ingredientspringboot.entity.MovementType;
import org.ingredient.ingredientspringboot.entity.UnitEnum;

public class StockMovementCreationDto {
    private UnitEnum unit;
    private double value;
    private MovementType type;

    public UnitEnum getUnit() { return unit; }
    public void setUnit(UnitEnum unit) { this.unit = unit; }

    public double getValue() { return value; }
    public void setValue(double value) { this.value = value; }

    public MovementType getType() { return type; }
    public void setType(MovementType type) { this.type = type; }
}
