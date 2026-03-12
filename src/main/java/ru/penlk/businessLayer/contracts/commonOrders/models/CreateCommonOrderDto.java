package ru.penlk.businessLayer.contracts.commonOrders.models;

import ru.penlk.dataAcessLayer.entities.cars.CarId;
import ru.penlk.dataAcessLayer.entities.orders.commonOrder.CommonOrder;
import ru.penlk.dataAcessLayer.entities.orders.commonOrder.CommonOrderId;
import ru.penlk.dataAcessLayer.entities.users.clients.ClientId;
import ru.penlk.dataAcessLayer.entities.users.managers.ManagerId;

public record CreateCommonOrderDto(CommonOrderStateContract state,
                                   long clientId,
                                   long managerId,
                                   long carId) {


    public static CommonOrder mapToModel(CreateCommonOrderDto dto) {
        return new CommonOrder(
                CommonOrderId.defaultId(),
                CommonOrderStateContract.mapToCommonOrderState(dto.state()),
                new ClientId(dto.clientId()),
                new ManagerId(dto.managerId()),
                new CarId(dto.carId())
        );
    }
}
