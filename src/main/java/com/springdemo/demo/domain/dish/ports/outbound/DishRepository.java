package com.springdemo.demo.domain.dish.ports.outbound;

import com.springdemo.demo.domain.dish.aggregate.Dish;
import java.util.Optional;
import java.util.UUID;

public interface DishRepository {
    Dish save(Dish dish);
    Optional<Dish> findById(UUID id);
    void deleteById(UUID id);
}