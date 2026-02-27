package ru.penlk.dataBase.entities.orders.specialOrder;

public record SpecialOrderId(long id) {
    public SpecialOrderId {
        if (id < 0) {
            throw new IllegalArgumentException("Id must be non-negative");
        }
    }

    public static SpecialOrderId defaultId() {
        return new SpecialOrderId(0);
    }
}