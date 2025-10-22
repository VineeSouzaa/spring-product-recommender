package com.springdemo.demo.adapters.inbound.rest;

import com.springdemo.demo.adapters.outbound.persistance.DishEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaDishRepository extends JpaRepository<DishEntity, UUID> {
}