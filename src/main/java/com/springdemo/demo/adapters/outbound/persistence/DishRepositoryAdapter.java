package com.springdemo.demo.adapters.outbound.persistence;

import com.springdemo.demo.domain.dish.aggregate.Dish;
import com.springdemo.demo.domain.dish.ports.outbound.DishRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@Repository
public class DishRepositoryAdapter implements DishRepository {

    private final JpaDishRepository jpaRepository;
    private final DishMapper mapper;

    public DishRepositoryAdapter(JpaDishRepository jpaRepository, DishMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Dish save(Dish dish) {
        Optional<DishEntity> existingEntity = jpaRepository.findById(dish.getId());

        if (existingEntity.isPresent()) {
            DishEntity entity = existingEntity.get();
            entity.setName(dish.getName());
            entity.setActive(dish.isActive());
            entity.setUpdatedAt(dish.getUpdatedAt());
            DishEntity saved = jpaRepository.save(entity);
            return mapper.toDomain(saved);
        } else {
            DishEntity entity = mapper.toEntity(dish);
            DishEntity saved = jpaRepository.save(entity);
            return mapper.toDomain(saved);
        }
    }

    @Override
    public Dish[] findAllByActiveTrue() {
        DishEntity[] dishes = jpaRepository.findAllByActiveTrue().toArray(new DishEntity[0]);
        return Arrays.stream(dishes).map(mapper::toDomain).toArray(Dish[]::new);
    }

    @Override
    public Optional<Dish> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }
}
