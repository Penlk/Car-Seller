package ru.penlk.dataAcessLayer.entities.cars;

public record CarId(long id) {
    public CarId {
        if (id < 0) {
            throw new IllegalArgumentException("Id must be non-negative");
        }
    }

    public static CarId defaultId() {
        return new CarId(0);
    }
}
