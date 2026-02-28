package ru.penlk.dataAcessLayer.entities.carParts;

public record CarPartId(long id) {
    public CarPartId {
        if (id < 0) {
            throw new IllegalArgumentException("Id must be non-negative");
        }
    }

    public static CarPartId defaultId() {
        return new CarPartId(0);
    }
}