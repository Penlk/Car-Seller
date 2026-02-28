package ru.penlk.dataBase.entities.nodes;

public record NodeId(long id) {
    public NodeId {
        if (id < 0) {
            throw new IllegalArgumentException("Id must be non-negative");
        }
    }

    public static NodeId defaultId() {
        return new NodeId(0);
    }
}