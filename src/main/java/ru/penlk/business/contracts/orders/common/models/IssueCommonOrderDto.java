package ru.penlk.business.contracts.orders.common.models;

import ru.penlk.dao.entities.orders.commonOrder.CommonOrder;
import ru.penlk.dao.entities.valueObjects.Price;

import java.math.BigDecimal;

public record IssueCommonOrderDto(long orderId,
                                  CommonOrderStateContract state,
                                  long clientId,
                                  long carId,
                                  BigDecimal price) {

    public static IssueCommonOrderDto mapToDto(CommonOrder order, Price price) {
        return new IssueCommonOrderDto(
                order.getId().id(),
                CommonOrderStateContract.mapToContract(order.getState()),
                order.getClientId().id(),
                order.getCarId().id(),
                price.value()
        );
    }
}
