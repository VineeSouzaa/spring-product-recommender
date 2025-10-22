package com.springdemo.demo.adapters.inbound.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DishRequest(
        @NotBlank(message = "Name is required")
        @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
        String name,

        Boolean active
) {
    public DishRequest {
        if (active == null) {
            active = true;
        }
    }
}