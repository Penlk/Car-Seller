package ru.penlk.businessLayer.contracts.specialOrders.models;

import java.math.BigDecimal;

public record IssueSpecialOrderDto(long orderId,
                                   String state,
                                   long clientId,
                                   long carId,
                                   BigDecimal price) {
}
