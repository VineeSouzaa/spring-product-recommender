package com.springdemo.demo.application.dish;

import com.springdemo.demo.domain.dish.aggregate.Dish;
import com.springdemo.demo.domain.dish.ports.outbound.DishRepository;
import java.util.Optional;
import java.util.UUID;

public class DishService {

    private final DishRepository dishRepository;

    public DishService(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    public Dish createDish(String name, boolean active) {
        Dish dish = Dish.create(name, active);
        return dishRepository.save(dish);
    }

    public Optional<Dish> getDishById(UUID id) {
        return dishRepository.findById(id);
    }

    public Dish[] getDishes() {
        return dishRepository.findAllByActiveTrue();
    }

    public Dish updateDish(UUID id, String name, Boolean active) {
        Dish dish = dishRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Dish not found: " + id));

        if (name != null) {
            dish.updateName(name);
        }

        if (active != null) {
            if (active) {
                dish.activate();
            } else {
                dish.deactivate();
            }
        }

        return dishRepository.save(dish);
    }

    public void deleteDish(UUID id) {
        dishRepository.deleteById(id);
    }


}