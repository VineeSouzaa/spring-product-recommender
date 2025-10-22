package com.springdemo.demo.domain.dish.aggregate;

import java.sql.Timestamp;
import java.util.UUID;

public class Dish {
    private final UUID id;
    private final String name;
    private final boolean active;
    private final Timestamp createdAt;
    private final Timestamp updatedAt;

    private Dish(UUID id, String name, boolean active, Timestamp createdAt, Timestamp updatedAt) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Dish name cannot be empty");
        }
        this.id = id;
        this.name = name;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Dish create(String name) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        return new Dish(
                UUID.randomUUID(),
                name,
                true,
                now,
                now
        );
    }

    public static Dish reconstitute(UUID id, String name, boolean active,
                                    Timestamp createdAt, Timestamp updatedAt) {
        return new Dish(id, name, active, createdAt, updatedAt);
    }

    public Dish deactivate() {
        if (!this.active) {
            return this;
        }
        return new Dish(
                this.id,
                this.name,
                false,
                this.createdAt,
                new Timestamp(System.currentTimeMillis())
        );
    }

    public Dish activate() {
        if (this.active) {
            return this;
        }
        return new Dish(
                this.id,
                this.name,
                true,
                this.createdAt,
                new Timestamp(System.currentTimeMillis())
        );
    }

    public Dish updateName(String newName) {
        if (newName == null || newName.isBlank()) {
            throw new IllegalArgumentException("Dish name cannot be empty");
        }
        if (this.name.equals(newName)) {
            return this;
        }
        return new Dish(
                this.id,
                newName,
                this.active,
                this.createdAt,
                new Timestamp(System.currentTimeMillis())
        );
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dish dish = (Dish) o;
        return id != null && id.equals(dish.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", active=" + active +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}