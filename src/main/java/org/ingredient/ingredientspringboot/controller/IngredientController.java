package org.ingredient.ingredientspringboot.controller;

import org.ingredient.ingredientspringboot.dto.IngredientResponse;
import org.ingredient.ingredientspringboot.entity.Ingredient;
import org.ingredient.ingredientspringboot.mapper.IngredientMapper;
import org.ingredient.ingredientspringboot.service.IngredientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
