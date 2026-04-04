package ru.penlk.business.contracts.orders.special.models;

import ru.penlk.dao.entities.orders.special.SpecialOrder;
import ru.penlk.dao.entities.vo.Price;

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
