package ru.penlk.businessLayer.contracts.specialOrders.models;

import ru.penlk.dataAcessLayer.entities.cars.CarId;
import ru.penlk.dataAcessLayer.entities.orders.specialOrder.SpecialOrder;
import ru.penlk.dataAcessLayer.entities.orders.specialOrder.SpecialOrderId;
import ru.penlk.dataAcessLayer.entities.users.clients.ClientId;
import ru.penlk.dataAcessLayer.entities.users.managers.ManagerId;
import ru.penlk.dataAcessLayer.entities.valueObjects.Price;

import java.math.BigDecimal;

public record SpecialOrderDto(long orderId,
                              SpecialOrderStateContract state,
                              long clientId,
                              long managerId,
                              long carId,
                              BigDecimal price) {

    public static SpecialOrderDto mapToDto(SpecialOrder specialOrder, Price price) {
        return new SpecialOrderDto(
                specialOrder.getId().id(),
                SpecialOrderStateContract.mapToContract(specialOrder.getState()),
                specialOrder.getClientId().id(),
                specialOrder.getManagerId().id(),
                specialOrder.getCarId().id(),
                price.value()
        );
    }

    public static SpecialOrder mapToModel(SpecialOrderDto dto) {
        return new SpecialOrder(
                new SpecialOrderId(dto.orderId()),
                SpecialOrderStateContract.mapToSpecialOrderState(dto.state()),
                new ClientId(dto.clientId()),
                new ManagerId(dto.managerId()),
                new CarId(dto.carId())
        );
    }
}
