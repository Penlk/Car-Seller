package ru.penlk.dataBase.engines;

public record EngineId(long id) {
    public EngineId {
        if (id < 0) {
            throw new IllegalArgumentException("Id must be non-negative");
        }
    }

    public static EngineId defaultId() {
        return new EngineId(0);
    }
}
