package com.springdemo.demo.domain.dish.ports.inbound;


import java.util.UUID;

public interface DishInbound {
    DishCreated create(String name);
    DishActivated activate(UUID dishId);
    DishDeactivated deactivate(UUID dishId);
    DishUpdated update(UUID dishId, String newName);
}