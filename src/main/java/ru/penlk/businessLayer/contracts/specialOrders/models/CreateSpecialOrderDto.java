package ru.penlk.businessLayer.contracts.specialOrders.models;

import ru.penlk.dataAcessLayer.entities.cars.CarId;
import ru.penlk.dataAcessLayer.entities.orders.specialOrder.SpecialOrder;
import ru.penlk.dataAcessLayer.entities.orders.specialOrder.SpecialOrderId;
import ru.penlk.dataAcessLayer.entities.users.clients.ClientId;
import ru.penlk.dataAcessLayer.entities.users.managers.ManagerId;

import java.util.Collection;

public record CreateSpecialOrderDto(SpecialOrderStateContract state,
                                    long clientId,
                                    long managerId,
                                    long carId,
                                    Collection<SpecialOrderDto> specialOrders) {

    public static SpecialOrder mapToModel(CreateSpecialOrderDto dto) {
        return new SpecialOrder(
                SpecialOrderId.defaultId(),
                SpecialOrderStateContract.mapToSpecialOrderState(dto.state()),
                new ClientId(dto.clientId()),
                new ManagerId(dto.managerId()),
                new CarId(dto.carId())
        );
    }
}
