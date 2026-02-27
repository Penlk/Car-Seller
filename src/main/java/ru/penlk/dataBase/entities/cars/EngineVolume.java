package ru.penlk.dataBase.entities.cars;

import java.math.BigDecimal;

/**
 *
 * @param value measured in watts
 */
public record EngineVolume(BigDecimal value) {
    public EngineVolume {
        if (value.compareTo(new BigDecimal(0)) < 0) {
            throw new IllegalArgumentException("Volume must be non-negative");
        }
    }
}
