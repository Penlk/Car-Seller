package ru.penlk.dataAcessLayer.entities.valueObjects;

import lombok.NonNull;

import java.math.BigDecimal;

public record Price(BigDecimal value) implements Comparable<Price> {
    public Price {
        if (value.compareTo(new BigDecimal(0)) < 0) {
            throw new IllegalArgumentException("Price must be non-negative");
        }
    }

    @Override
    public int compareTo(@NonNull Price price) {
        return value.compareTo(price.value);
    }
}