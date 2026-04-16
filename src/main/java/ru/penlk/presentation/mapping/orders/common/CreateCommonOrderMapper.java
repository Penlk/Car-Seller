package ru.penlk.presentation.mapping.orders.common;

import org.mapstruct.Mapper;
import ru.penlk.dao.entities.orders.common.CommonOrder;
import ru.penlk.presentation.orders.common.models.CreateCommonOrderDto;

@Mapper(componentModel = "spring")
public interface CreateCommonOrderMapper {
    CommonOrder createCommonOrderDtoToCommonOrder(CreateCommonOrderDto commonOrderDto);
}
