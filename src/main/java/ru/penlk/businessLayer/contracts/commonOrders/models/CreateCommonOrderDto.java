package ru.penlk.businessLayer.contracts.commonOrders.models;

public record CreateCommonOrderDto(String state,
                                   long clientId,
                                   long managerId,
                                   long carId) {

}
