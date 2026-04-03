package ru.penlk.dao.entities.vo;

import jakarta.persistence.Embeddable;
import lombok.NonNull;

import java.math.BigDecimal;

/**
 *
 * @param value measured in watts
 */
@Embeddable
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
