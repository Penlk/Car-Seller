package ru.penlk.presentation.configurations.models;

import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record CreateConfiguratorDto(
        @NotNull Long carId,
        @NotNull Set<Long> specialConfigurationIds
) {
}
