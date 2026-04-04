package ru.penlk.presentation.cars.parts.models;

import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;

public record CarPartDto(@NonNull Long id,
                         @NotBlank String namePart,
                         @NonNull Long nodeId) {
}
