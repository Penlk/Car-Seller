package ru.penlk.presentation.mapping.orders.special;

import org.mapstruct.Mapper;
import ru.penlk.dao.entities.orders.special.SpecialOrder;
import ru.penlk.presentation.orders.special.models.CreateSpecialOrderDto;

@Mapper(componentModel = "spring")
public interface CreateSpecialOrderMapper {
    SpecialOrder createSpecialOrderDtoToSpecialOrder(CreateSpecialOrderDto specialOrderDto);
}
