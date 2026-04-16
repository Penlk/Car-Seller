package ru.penlk.presentation.mapping.orders.common;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.penlk.dao.entities.orders.common.CommonOrder;
import ru.penlk.presentation.orders.common.models.CommonOrderDto;

@Mapper(componentModel = "spring", uses = {
        CarIdMapper.class,
        ClientIdMapper.class,
        ManagerIdMapper.class
})
public interface CommonOrderMapper {
    CommonOrder commonOrderDtoToCommonOrder(CommonOrderDto commonOrderDto);

    @Mapping(source = "client", target = "clientId")
    @Mapping(source = "manager", target = "managerId")
    @Mapping(source = "car", target = "carId")
    CommonOrderDto commonOrderToCommonOrderDto(CommonOrder commonOrder);
}
