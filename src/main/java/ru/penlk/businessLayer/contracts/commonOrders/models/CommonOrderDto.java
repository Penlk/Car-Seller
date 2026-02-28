package ru.penlk.businessLayer.contracts.commonOrders.models;

public record CommonOrderDto(long orderId,
                             String state,
                             long clientId,
                             long managerId,
                             long carId) {

}
