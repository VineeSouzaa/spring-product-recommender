package com.springdemo.demo.application.dish;

import com.springdemo.demo.domain.dish.aggregate.Dish;
import com.springdemo.demo.domain.dish.ports.inbound.*;
import com.springdemo.demo.domain.dish.ports.outbound.DishRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class DishService implements DishInbound {

    private final DishRepository dishRepository;


    public DishService(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    @Override
    public DishCreated create(String name) {
        Dish dish = Dish.create(name);
        Dish saved = dishRepository.save(dish);
        return new DishCreated(saved);
    }

    @Override
    public DishActivated activate(UUID dishId) {
        return dishRepository.findById(dishId)
                .map(dish -> {
                    Dish activated = dish.activate();
                    dishRepository.save(activated);
                    return new DishActivated(true);
                })
                .orElse(new DishActivated(false));
    }

    @Override
    public DishDeactivated deactivate(UUID dishId) {
        return dishRepository.findById(dishId)
                .map(dish -> {
                    Dish deactivated = dish.deactivate();
                    dishRepository.save(deactivated);
                    return new DishDeactivated(true);
                })
                .orElse(new DishDeactivated(false));
    }

    @Override
    public DishUpdated update(UUID dishId, String newName) {
        return dishRepository.findById(dishId)
                .map(dish -> {
                    Dish updated = dish.updateName(newName);
                    Dish saved = dishRepository.save(updated);
                    return new DishUpdated(saved);
                })
                .orElse(new DishUpdated(null));
    }
}