package org.ingredient.ingredientspringboot.controller;

import org.ingredient.ingredientspringboot.dto.DishIngredientUpdateRequest;
import org.ingredient.ingredientspringboot.dto.DishResponse;
import org.ingredient.ingredientspringboot.exception.BadRequestException;
import org.ingredient.ingredientspringboot.service.DishService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dishes")
public class DishController {

    private final DishService service;

    public DishController(DishService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<DishResponse>> getAllDishes() {
        List<DishResponse> responses = service.getAllDishes();

        if (responses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(responses);
    }
    @PutMapping("/{id}/ingredients")
    public ResponseEntity<Void> updateIngredients(
            @PathVariable("id") Long id,
            @RequestBody(required = true) List<DishIngredientUpdateRequest> ingredients) {
        if (ingredients == null || ingredients.isEmpty()) {
            throw new BadRequestException("Le corps de la requête est obligatoire et ne doit pas être vide.");
        }
        service.updateDishIngredients(id, ingredients);
        return ResponseEntity.noContent().build();
    }
}