package ru.penlk.presentation.configurations.models;

import lombok.NonNull;

import java.util.Set;

public record CreateConfiguratorDto(
        @NonNull Long carId,
        @NonNull Set<Long> specialConfigurationIds
) {
}
