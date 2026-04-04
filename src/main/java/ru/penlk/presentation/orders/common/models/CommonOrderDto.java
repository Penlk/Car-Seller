package ru.penlk.presentation.orders.common.models;

import ru.penlk.dao.entities.orders.common.CommonOrder;

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
