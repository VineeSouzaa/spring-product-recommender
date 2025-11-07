package com.springdemo.demo.adapters.inbound.rest;

import com.springdemo.demo.adapters.inbound.rest.dto.DishRequest;
import com.springdemo.demo.adapters.inbound.rest.dto.DishResponse;
import com.springdemo.demo.application.dish.DishService;
import com.springdemo.demo.domain.dish.aggregate.Dish;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.UUID;

@RestController
@RequestMapping("/api/dishes")
public class DishController {

    private final DishService dishService;

    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping
    public ResponseEntity<DishResponse[]> findAllByActiveTrue() {
        Dish[] dishes = dishService.getDishes();
        DishResponse[] responses = Arrays.stream(dishes).map(this::toResponse)
                .toArray(DishResponse[]::new);
        return ResponseEntity.ok(responses);
    }


    @PostMapping
    public ResponseEntity<DishResponse> createDish(@Valid @RequestBody DishRequest request) {
        Dish dish = dishService.createDish(request.name(), request.active());
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(dish));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DishResponse> getDishById(@PathVariable UUID id) {
        return dishService.getDishById(id)
                .map(dish -> ResponseEntity.ok(toResponse(dish)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<DishResponse> updateDish(
            @PathVariable UUID id,
            @Valid @RequestBody DishRequest request) {
        try {
            Dish dish = dishService.updateDish(id, request.name(), request.active());
            return ResponseEntity.ok(toResponse(dish));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDish(@PathVariable UUID id) {
        dishService.deleteDish(id);
        return ResponseEntity.noContent().build();
    }

    private DishResponse toResponse(Dish dish) {
        return new DishResponse(
                dish.getId(),
                dish.getName(),
                dish.isActive(),
                dish.getCreatedAt(),
                dish.getUpdatedAt()
        );
    }
}