package org.ingredient.ingredientspringboot.controller;

import org.ingredient.ingredientspringboot.dto.IngredientResponse;
import org.ingredient.ingredientspringboot.dto.StockMovementResponse;
import org.ingredient.ingredientspringboot.dto.StockResponse;
import org.ingredient.ingredientspringboot.entity.Ingredient;
import org.ingredient.ingredientspringboot.exception.BadRequestException;
import org.ingredient.ingredientspringboot.mapper.IngredientMapper;
import org.ingredient.ingredientspringboot.service.IngredientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {

    private final IngredientService service;
    private final IngredientMapper mapper;

    public IngredientController(IngredientService service, IngredientMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<IngredientResponse>> getIngredients() {
        List<IngredientResponse> responses = service.getAllIngredients()
                .stream()
                .map(mapper::toResponse)
                .toList();

        if (responses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(responses);
    }
    @GetMapping("/{id}")
    public ResponseEntity<IngredientResponse> getIngredientById(@PathVariable("id") Integer id) {

        Ingredient ingredient = service.getById(id);

        IngredientResponse response = mapper.toResponse(ingredient);

        return ResponseEntity.ok(response);
    }
    @GetMapping("/{id}/stock")
    public ResponseEntity<StockResponse> getStockAt(
            @PathVariable("id") Integer id,
            @RequestParam(value = "at", required = false) LocalDateTime at,
            @RequestParam(value = "unit", required = false) String unit) {
        
        if (at == null || unit == null || unit.trim().isEmpty()) {
            throw new BadRequestException("Either mandatory query parameter `at` or `unit` is not provided.");
        }

        StockResponse response = service.getStockAt(id, at, unit);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/stockMovements")
    public ResponseEntity<List<StockMovementResponse>> getStockMovements(
            @PathVariable("id") Integer id,
            @RequestParam(value = "from", required = false) Instant from,
            @RequestParam(value = "to", required = false) Instant to) {

        List<StockMovementResponse> movements = service.getStockMovements(id, from, to);

        if (movements.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(movements);
    }
}
