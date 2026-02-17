package ru.penlk.dataBase.cars;

import java.math.BigDecimal;

public record CarPrice(BigDecimal value) {
    public CarPrice {
        if (value.compareTo(new BigDecimal(0)) < 0) {
            throw new IllegalArgumentException("Price must be non-negative");
        }
    }
}