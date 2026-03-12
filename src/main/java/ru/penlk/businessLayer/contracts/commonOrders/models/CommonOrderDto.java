package ru.penlk.businessLayer.contracts.commonOrders.models;

import ru.penlk.dataAcessLayer.entities.cars.CarId;
import ru.penlk.dataAcessLayer.entities.orders.commonOrder.CommonOrder;
import ru.penlk.dataAcessLayer.entities.orders.commonOrder.CommonOrderId;
import ru.penlk.dataAcessLayer.entities.users.clients.ClientId;
import ru.penlk.dataAcessLayer.entities.users.managers.ManagerId;

public record CommonOrderDto(long orderId,
                             CommonOrderStateContract state,
                             long clientId,
                             long managerId,
                             long carId) {

    public static CommonOrderDto mapToDto(CommonOrder commonOrder) {
        return new CommonOrderDto(
                commonOrder.getId().id(),
                CommonOrderStateContract.mapToContract(commonOrder.getState()),
                commonOrder.getClientId().id(),
                commonOrder.getManagerId().id(),
                commonOrder.getCarId().id()
        );
    }

    public static CommonOrder mapToModel(CommonOrderDto dto) {
        return new CommonOrder(
                new CommonOrderId(dto.orderId()),
                CommonOrderStateContract.mapToCommonOrderState(dto.state()),
                new ClientId(dto.clientId()),
                new ManagerId(dto.managerId()),
                new CarId(dto.carId())
        );
    }
}
