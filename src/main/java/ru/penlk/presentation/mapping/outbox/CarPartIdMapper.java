package ru.penlk.presentation.mapping.outbox;

import org.mapstruct.Mapper;
import ru.penlk.dao.entities.cars.CarPart;

@Mapper(componentModel = "spring")
public class CarPartIdMapper {
    public Long carPartToId(CarPart carPart) {
        return carPart.getId();
    }
}
