package ru.penlk.dataBase.entities.orders.commonOrder;

public record CommonOrderId(long id) {
    public CommonOrderId {
        if (id < 0) {
            throw new IllegalArgumentException("Id must be non-negative");
        }
    }

    public static CommonOrderId defaultId() {
        return new CommonOrderId(0);
    }
}