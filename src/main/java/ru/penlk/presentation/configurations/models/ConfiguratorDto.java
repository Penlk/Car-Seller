package ru.penlk.presentation.configurations.models;

import lombok.NonNull;

import java.util.Set;

public record ConfiguratorDto(
        @NonNull Long id,
        @NonNull Long carId,
        @NonNull Set<Long> specialConfigurationIds,
        @NonNull Set<Long> carPartIds,
        boolean isFinished) {
}
