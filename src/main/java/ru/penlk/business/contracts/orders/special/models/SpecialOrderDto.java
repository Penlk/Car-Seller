package ru.penlk.business.contracts.orders.special.models;

import ru.penlk.dao.entities.orders.special.SpecialOrder;
import ru.penlk.dao.entities.orders.special.SpecialOrderId;
import ru.penlk.dao.entities.vo.Price;

import java.math.BigDecimal;

public record SpecialOrderDto(long orderId,
                              SpecialOrderStateContract state,
                              long clientId,
                              long managerId,
                              long carId,
                              BigDecimal price) {

    public static SpecialOrderDto mapToDto(SpecialOrder specialOrder) {
        return new SpecialOrderDto(
                specialOrder.getId().id(),
                SpecialOrderStateContract.mapToContract(specialOrder.getState()),
                specialOrder.getClientId().id(),
                specialOrder.getManagerId().id(),
                specialOrder.getCarId().id(),
                specialOrder.getPrice().value()
        );
    }

    public static SpecialOrder mapToModel(SpecialOrderDto dto) {
        return new SpecialOrder(
                new SpecialOrderId(dto.orderId()),
                SpecialOrderStateContract.mapToSpecialOrderState(dto.state()),
                new ClientId(dto.clientId()),
                new ManagerId(dto.managerId()),
                new CarId(dto.carId()),
                new Price(dto.price())
        );
    }
}
