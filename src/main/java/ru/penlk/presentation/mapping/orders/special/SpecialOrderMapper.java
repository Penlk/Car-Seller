package ru.penlk.presentation.mapping.orders.special;

import org.mapstruct.Mapper;
import ru.penlk.dao.entities.orders.special.SpecialOrder;
import ru.penlk.presentation.mapping.vo.PriceMapper;
import ru.penlk.presentation.orders.special.models.SpecialOrderDto;

@Mapper(componentModel = "spring", uses = {PriceMapper.class})
public interface SpecialOrderMapper {
    SpecialOrder specialOrderDtoToSpecialOrder(SpecialOrderDto specialOrderDto);

    SpecialOrderDto specialOrderToSpecialOrderDto(SpecialOrder specialOrder);
}
