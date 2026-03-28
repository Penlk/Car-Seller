package ru.penlk.dao.entities.cars;

import jakarta.persistence.Embeddable;
import lombok.NonNull;

import java.math.BigDecimal;

/**
 *
 * @param value measured in liters
 */
@Embeddable
public record EnginePower(BigDecimal value) implements Comparable<EnginePower> {
    public EnginePower {
        if (value.compareTo(new BigDecimal(0)) < 0) {
            throw new IllegalArgumentException("Power must be non-negative");
        }
    }

    @Override
    public int compareTo(@NonNull EnginePower enginePower) {
        return value.compareTo(enginePower.value);
    }
}
