package ru.penlk.dao.entities.vo;

import jakarta.persistence.Embeddable;
import lombok.NonNull;

import java.math.BigDecimal;

@Embeddable
public record Price(BigDecimal price) implements Comparable<Price> {
    public final static Price ZERO = new Price(BigDecimal.ZERO);

    public Price {
        if (price.compareTo(new BigDecimal(0)) < 0) {
            throw new IllegalArgumentException("Price must be non-negative");
        }
    }

    @Override
    public int compareTo(@NonNull Price other) {
        return price.compareTo(other.price);
    }

    public Price add(Price other) {
        return new Price(price.add(other.price));
    }
}