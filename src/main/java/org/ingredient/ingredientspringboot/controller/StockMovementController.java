package org.ingredient.ingredientspringboot.controller;

import org.ingredient.ingredientspringboot.dto.StockMovementCreationDto;
import org.ingredient.ingredientspringboot.dto.StockMovementDto;
import org.ingredient.ingredientspringboot.entity.StockMovement;
import org.ingredient.ingredientspringboot.repository.IngredientRepository;
import org.ingredient.ingredientspringboot.repository.StockMovementRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/ingredients/{id}/stockMovements")
public class StockMovementController {

    private final StockMovementRepository stockMovementRepository;
    private final IngredientRepository ingredientRepository;

    public StockMovementController(StockMovementRepository stockMovementRepository,
                                   IngredientRepository ingredientRepository) {
        this.stockMovementRepository = stockMovementRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @GetMapping
    public ResponseEntity<?> getStockMovements(
            @PathVariable Long id,
            @RequestParam Instant from,
            @RequestParam Instant to) {
        List<StockMovementDto> result = stockMovementRepository
                .findByIngredientIdBetween(id, from, to)
                .stream()
                .map(StockMovementDto::new)
                .toList();

        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<?> addStockMovements(
            @PathVariable Long id,
            @RequestBody List<StockMovementCreationDto> creations) {
        List<StockMovement> toSave = creations.stream().map(dto -> {
            StockMovement sm = new StockMovement();
            sm.setCreatedAt(Instant.now());
            sm.setUnit(dto.getUnit());
            sm.setValue(dto.getValue());
            sm.setType(dto.getType());
            return sm;
        }).toList();

        List<StockMovementDto> saved = stockMovementRepository.saveAll(id, toSave)
                .stream()
                .map(StockMovementDto::new)
                .toList();

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}
