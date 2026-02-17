package ru.penlk.dataBase.engines;

import java.math.BigDecimal;

/**
 *
 * @param value measured in watts
 */
public record EnginePower(BigDecimal value) {
    public EnginePower {
        if (value.compareTo(new BigDecimal(0)) < 0) {
            throw new IllegalArgumentException("Price must be non-negative");
        }
    }
}
