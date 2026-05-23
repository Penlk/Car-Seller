package ru.penlk.presentation.orders.special.models;

import jakarta.validation.constraints.NotNull;
import ru.penlk.dao.entities.orders.special.SpecialOrderState;

import java.math.BigDecimal;

public record SpecialOrderDto(@NotNull Long id,
                              @NotNull SpecialOrderState state,
                              @NotNull String ownerId,
                              @NotNull String managerId,
                              @NotNull Long carId,
                              @NotNull BigDecimal price) {
}
