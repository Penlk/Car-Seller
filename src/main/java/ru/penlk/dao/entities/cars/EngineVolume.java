package ru.penlk.dao.entities.cars;

import lombok.NonNull;

import java.math.BigDecimal;

/**
 *
 * @param value measured in watts
 */
public record EngineVolume(BigDecimal value) implements Comparable<EngineVolume> {
    public EngineVolume {
        if (value.compareTo(new BigDecimal(0)) < 0) {
            throw new IllegalArgumentException("Volume must be non-negative");
        }
    }

    @Override
    public int compareTo(@NonNull EngineVolume engineVolume) {
        return value.compareTo(engineVolume.value);
    }
}
