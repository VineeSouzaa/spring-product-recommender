package com.springdemo.demo.adapters.inbound.rest.dto;

import java.sql.Timestamp;
import java.util.UUID;

public record DishResponse(
        UUID id,
        String name,
        boolean active,
        Timestamp createdAt,
        Timestamp updatedAt
) {}