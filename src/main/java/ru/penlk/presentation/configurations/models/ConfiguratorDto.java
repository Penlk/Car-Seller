package ru.penlk.presentation.configurations.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record ConfiguratorDto(
        @NotNull Long id,
        @NotNull Long carId,
        @NotEmpty String ownerId,
        @NotNull Set<Long> specialConfigurationIds,
        @NotNull Set<Long> carPartIds,
        boolean isFinished) {
}
