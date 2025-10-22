package com.springdemo.demo.domain.dish.ports.inbound;

import com.springdemo.demo.domain.dish.aggregate.Dish;

public record DishUpdated(Dish dish) {}

