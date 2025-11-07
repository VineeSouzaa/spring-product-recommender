package com.springdemo.demo.domain.dish.events;

import com.springdemo.demo.domain.dish.aggregate.Dish;

public record DishUpdated(Dish dish) {}

