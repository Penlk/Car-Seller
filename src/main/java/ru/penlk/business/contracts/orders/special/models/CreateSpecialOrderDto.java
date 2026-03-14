package ru.penlk.business.contracts.orders.special.models;

import ru.penlk.dao.entities.cars.CarId;
import ru.penlk.dao.entities.orders.specialOrder.SpecialOrder;
import ru.penlk.dao.entities.orders.specialOrder.SpecialOrderId;
import ru.penlk.dao.entities.users.clients.ClientId;
import ru.penlk.dao.entities.users.managers.ManagerId;
import ru.penlk.dao.entities.valueObjects.Price;

import java.util.Collection;

public record CreateSpecialOrderDto(SpecialOrderStateContract state,
                                    long clientId,
                                    long managerId,
                                    long carId,
                                    Collection<Long> specialCarPartIds) {

    public static SpecialOrder mapToModel(CreateSpecialOrderDto dto, Price price) {
        return new SpecialOrder(
                SpecialOrderId.defaultId(),
                SpecialOrderStateContract.mapToSpecialOrderState(dto.state()),
                new ClientId(dto.clientId()),
                new ManagerId(dto.managerId()),
                new CarId(dto.carId()),
                price
        );
    }
}
