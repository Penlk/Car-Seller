package ru.penlk.presentation.nodes.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NodeDto(@NotNull Long id,
                      @NotBlank String name) {
}
