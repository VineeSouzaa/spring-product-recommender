package com.springdemo.demo.adapters.inbound.rest;

import com.springdemo.demo.application.dish.DishService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.springdemo.demo.domain.dish.ports.outbound.DishRepository;

@Service
@Transactional
public class DishServiceAdapter extends DishService {
    public DishServiceAdapter(DishRepository dishRepository) {
        super(dishRepository);
    }
}
