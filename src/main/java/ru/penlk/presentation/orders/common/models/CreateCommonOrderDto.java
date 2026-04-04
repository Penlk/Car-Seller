package ru.penlk.business.contracts.orders.common.models;

import ru.penlk.dao.entities.orders.common.CommonOrder;

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
