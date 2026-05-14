package ru.penlk.presentation.orders.common.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import ru.penlk.dao.entities.orders.common.CommonOrderState;

public record CommonOrderDto(@NotNull Long id,
                             @NotNull CommonOrderState state,
                             @NotEmpty String ownerId,
                             @NotEmpty String managerId,
                             @NotNull Long carId) {
}
