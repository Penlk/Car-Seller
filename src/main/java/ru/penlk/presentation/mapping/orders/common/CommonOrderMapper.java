package ru.penlk.presentation.mapping.orders.common;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.penlk.dao.entities.orders.common.CommonOrder;
import ru.penlk.presentation.orders.common.models.CommonOrderDto;

@Mapper(componentModel = "spring", uses = {
        CarIdMapper.class,
})
public interface CommonOrderMapper {
    CommonOrder commonOrderDtoToCommonOrder(CommonOrderDto commonOrderDto);

    @Mapping(source = "car", target = "carId")
    CommonOrderDto commonOrderToCommonOrderDto(CommonOrder commonOrder);
}
