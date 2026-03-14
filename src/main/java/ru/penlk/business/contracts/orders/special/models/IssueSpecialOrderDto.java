package ru.penlk.businessLayer.contracts.orders.special.models;

import ru.penlk.dao.entities.orders.specialOrder.SpecialOrder;
import ru.penlk.dao.entities.valueObjects.Price;

import java.math.BigDecimal;

public record IssueSpecialOrderDto(long orderId,
                                   SpecialOrderStateContract state,
                                   long clientId,
                                   long carId,
                                   BigDecimal price) {

    public static IssueSpecialOrderDto mapToDto(SpecialOrder order, Price price) {
        return new IssueSpecialOrderDto(
                order.getId().id(),
                SpecialOrderStateContract.mapToContract(order.getState()),
                order.getClientId().id(),
                order.getCarId().id(),
                price.value()
        );
    }
}
