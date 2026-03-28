package ru.penlk.dao.entities.nodes;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public record NodeId(@Id long id) {
    public NodeId {
        if (id < 0) {
            throw new IllegalArgumentException("Id must be non-negative");
        }
    }

    public static NodeId defaultId() {
        return new NodeId(0);
    }
}