package ru.penlk.dataBase.entities.cars;

import java.math.BigDecimal;

/**
 *
 * @param value measured in liters
 */
public record EnginePower(BigDecimal value) {
    public EnginePower {
        if (value.compareTo(new BigDecimal(0)) < 0) {
            throw new IllegalArgumentException("Power must be non-negative");
        }
    }
}
