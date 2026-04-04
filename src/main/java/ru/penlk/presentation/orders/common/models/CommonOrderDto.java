package ru.penlk.presentation.orders.common.models;

import jakarta.validation.constraints.NotNull;
import ru.penlk.dao.entities.orders.common.CommonOrderState;

public record CommonOrderDto(@NotNull Long orderId,
                             @NotNull CommonOrderState state,
                             @NotNull Long clientId,
                             Long managerId,
                             @NotNull Long carId) {
}
