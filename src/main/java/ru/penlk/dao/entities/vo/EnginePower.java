package ru.penlk.dao.entities.vo;

import jakarta.persistence.Embeddable;
import lombok.NonNull;

import java.math.BigDecimal;

/**
 *
 * @param engine_power measured in liters
 */
@Embeddable
public record EnginePower(BigDecimal engine_power) implements Comparable<EnginePower> {
    public EnginePower {
        if (engine_power.compareTo(new BigDecimal(0)) < 0) {
            throw new IllegalArgumentException("Power must be non-negative");
        }
    }

    @Override
    public int compareTo(@NonNull EnginePower enginePower) {
        return engine_power.compareTo(enginePower.engine_power);
    }
}
