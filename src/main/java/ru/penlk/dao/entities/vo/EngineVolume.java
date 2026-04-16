package ru.penlk.dao.entities.vo;

import jakarta.persistence.Embeddable;
import lombok.NonNull;

import java.math.BigDecimal;

/**
 *
 * @param engine_volume measured in watts
 */
@Embeddable
public record EngineVolume(BigDecimal engine_volume) implements Comparable<EngineVolume> {
    public EngineVolume {
        if (engine_volume.compareTo(new BigDecimal(0)) < 0) {
            throw new IllegalArgumentException("Volume must be non-negative");
        }
    }

    @Override
    public int compareTo(@NonNull EngineVolume engineVolume) {
        return engine_volume.compareTo(engineVolume.engine_volume);
    }
}
