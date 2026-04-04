package ru.penlk.presentation.cars.parts.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateCarPartDto(@NotBlank String namePart,
                               @NotNull Long nodeId) {

}
