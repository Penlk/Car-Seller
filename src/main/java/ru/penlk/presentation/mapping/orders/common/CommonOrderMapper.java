package ru.penlk.presentation.mapping.orders.common;

import org.mapstruct.Mapper;
import ru.penlk.dao.entities.orders.common.CommonOrder;
import ru.penlk.presentation.orders.common.models.CommonOrderDto;

@Mapper(componentModel = "spring")
public interface CommonOrderMapper {
    CommonOrder commonOrderDtoToCommonOrder(CommonOrderDto commonOrderDto);

    CommonOrderDto commonOrderToCommonOrderDto(CommonOrder commonOrder);
}
