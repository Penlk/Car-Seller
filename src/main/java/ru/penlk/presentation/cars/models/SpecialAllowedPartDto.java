package ru.penlk.presentation.cars.models;

import lombok.NonNull;

import java.math.BigDecimal;

public record SpecialAllowedPartDto(
        @NonNull BigDecimal price,
        @NonNull Long carPartId
) {
}
