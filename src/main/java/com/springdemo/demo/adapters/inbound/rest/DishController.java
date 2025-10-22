package com.springdemo.demo.adapters.inbound.rest;

import com.springdemo.demo.adapters.outbound.persistance.DishEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/dishes")
public class DishController {

    private final JpaDishRepository dishRepository;

    @Autowired
    public DishController(JpaDishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    @GetMapping
    public ResponseEntity<List<DishEntity>> getAllDishes() {
        List<DishEntity> dishes = dishRepository.findAll();
        return ResponseEntity.ok(dishes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DishEntity> getDishById(@PathVariable UUID id) {
        return dishRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DishEntity> createDish(@RequestBody DishEntity dish) {
        DishEntity savedDish = dishRepository.save(dish);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDish);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DishEntity> updateDish(@PathVariable UUID id, @RequestBody DishEntity dishDetails) {
        return dishRepository.findById(id)
                .map(dish -> {
                    // Update fields as needed
                    DishEntity updatedDish = dishRepository.save(dishDetails);
                    return ResponseEntity.ok(updatedDish);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDish(@PathVariable UUID id) {
        if (dishRepository.existsById(id)) {
            dishRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}