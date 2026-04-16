package ru.penlk.presentation.orders.special.models;

import jakarta.validation.constraints.NotNull;
import ru.penlk.dao.entities.orders.special.SpecialOrderState;

public record CreateSpecialOrderDto(@NotNull Long clientId,
                                    @NotNull Long configurationId) {
}
