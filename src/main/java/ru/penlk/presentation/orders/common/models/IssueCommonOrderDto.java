package ru.penlk.presentation.orders.common.models;

import jakarta.validation.constraints.NotNull;
import ru.penlk.dao.entities.orders.common.CommonOrder;
import ru.penlk.dao.entities.orders.common.CommonOrderState;
import ru.penlk.dao.entities.vo.Price;

import java.math.BigDecimal;

public record IssueCommonOrderDto(@NotNull Long orderId,
                                  @NotNull CommonOrderState state,
                                  @NotNull Long clientId,
                                  @NotNull Long carId,
                                  @NotNull BigDecimal price) {
}
