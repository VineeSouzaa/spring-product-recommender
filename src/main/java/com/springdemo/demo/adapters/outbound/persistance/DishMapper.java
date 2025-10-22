package com.springdemo.demo.adapters.outbound.persistance;

import com.springdemo.demo.domain.dish.aggregate.Dish;
import org.springframework.stereotype.Component;

@Component
public class DishMapper {

    public DishEntity toEntity(Dish dish) {
        return new DishEntity(
                dish.getId(),
                dish.getName(),
                dish.isActive(),
                dish.getCreatedAt(),
                dish.getUpdatedAt()
        );
    }

    public Dish toDomain(DishEntity entity) {
        return Dish.reconstitute(
                entity.getId(),
                entity.getName(),
                entity.isActive(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}