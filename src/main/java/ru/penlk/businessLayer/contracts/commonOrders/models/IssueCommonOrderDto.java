package ru.penlk.businessLayer.contracts.commonOrders.models;

import ru.penlk.dataAcessLayer.entities.orders.commonOrder.CommonOrder;

public record IssueCommonOrderDto(long orderId,
                                  CommonOrderStateContract state,
                                  long clientId,
                                  long carId) {

    public static IssueCommonOrderDto mapToDto(CommonOrder order) {
        return new IssueCommonOrderDto(
                order.getId().id(),
                CommonOrderStateContract.mapToContract(order.getState()),
                order.getClientId().id(),
                order.getCarId().id()
        );
    }
}
