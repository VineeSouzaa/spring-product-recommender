package com.springdemo.demo.domain.dish.ports.inbound;

import com.springdemo.demo.domain.dish.events.DishActivated;
import com.springdemo.demo.domain.dish.events.DishCreated;
import com.springdemo.demo.domain.dish.events.DishDeactivated;
import com.springdemo.demo.domain.dish.events.DishUpdated;

import java.util.UUID;

public interface DishInbound {
    DishCreated create(String name);
    DishActivated activate(UUID dishId);
    DishDeactivated deactivate(UUID dishId);
    DishUpdated update(UUID dishId, String newName);
}