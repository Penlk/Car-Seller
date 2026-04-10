package ru.penlk.presentation.mapping.orders.common;

import org.mapstruct.Mapper;
import ru.penlk.dao.entities.orders.common.CommonOrder;
import ru.penlk.presentation.orders.common.models.IssueCommonOrderDto;

@Mapper(componentModel = "spring")
public interface IssueCommonOrderMapper {
    CommonOrder issueCommonOrderDtoToCommonOrder(IssueCommonOrderDto dto);
}
