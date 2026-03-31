package ru.penlk.dao.entities.vo;

import jakarta.persistence.Embeddable;
import lombok.NonNull;

import java.math.BigDecimal;

@Embeddable
public record Price(BigDecimal value) implements Comparable<Price> {
    public final static Price ZERO = new Price(BigDecimal.ZERO);

    public Price {
        if (value.compareTo(new BigDecimal(0)) < 0) {
            throw new IllegalArgumentException("Price must be non-negative");
        }
    }

    @Override
    public int compareTo(@NonNull Price price) {
        return value.compareTo(price.value);
    }

    public Price add(Price other) {
        return new Price(value.add(other.value));
    }
}