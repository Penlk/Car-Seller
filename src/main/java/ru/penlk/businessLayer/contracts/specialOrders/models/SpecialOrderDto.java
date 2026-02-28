package ru.penlk.businessLayer.contracts.specialOrders.models;

import java.math.BigDecimal;

public record SpecialOrderDto(long orderId,
                              String state,
                              long clientId,
                              long managerId,
                              long carId,
                              BigDecimal price) {

}
