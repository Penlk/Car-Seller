package ru.penlk.presentation.orders.special.models;

import jakarta.validation.constraints.NotNull;
import ru.penlk.dao.entities.orders.special.SpecialOrderState;

import java.math.BigDecimal;

public record SpecialOrderDto(@NotNull Long orderId,
                              @NotNull SpecialOrderState state,
                              @NotNull Long clientId,
                              @NotNull Long managerId,
                              @NotNull Long carId,
                              @NotNull BigDecimal price) {
}
