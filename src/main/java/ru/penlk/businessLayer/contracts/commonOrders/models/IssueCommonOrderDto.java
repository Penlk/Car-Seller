package ru.penlk.businessLayer.contracts.commonOrders.models;

public record IssueCommonOrderDto(long orderId,
                                  String state,
                                  long clientId,
                                  long carId) {
}
