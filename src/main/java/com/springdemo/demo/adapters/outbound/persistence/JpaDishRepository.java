package com.springdemo.demo.adapters.outbound.persistence;

import com.springdemo.demo.domain.dish.aggregate.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
import java.util.List;

public interface JpaDishRepository extends JpaRepository<DishEntity, UUID> {
    List<DishEntity> findAllByActiveTrue();
}