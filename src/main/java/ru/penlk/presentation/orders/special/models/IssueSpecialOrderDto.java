package ru.penlk.presentation.orders.special.models;

import jakarta.validation.constraints.NotNull;
import ru.penlk.dao.entities.orders.special.SpecialOrder;
import ru.penlk.dao.entities.orders.special.SpecialOrderState;
import ru.penlk.dao.entities.vo.Price;

import java.math.BigDecimal;

public record IssueSpecialOrderDto(@NotNull Long orderId,
                                   @NotNull SpecialOrderState state,
                                   @NotNull Long clientId,
                                   @NotNull Long carId,
                                   @NotNull BigDecimal price) {
}
