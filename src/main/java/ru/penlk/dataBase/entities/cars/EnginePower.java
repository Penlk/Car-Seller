package ru.penlk.dataBase.entities.cars;

import lombok.NonNull;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @param value measured in liters
 */
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
