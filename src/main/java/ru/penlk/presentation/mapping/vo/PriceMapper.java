package ru.penlk.presentation.mapping.vo;

import org.mapstruct.Mapper;
import ru.penlk.dao.entities.vo.Price;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public class PriceMapper {
    public Price mapPrice(BigDecimal price) {
        return new Price(price);
    }

    public BigDecimal mapRaw(Price price) {
        return price.price();
    }
}
