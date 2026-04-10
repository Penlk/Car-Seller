package ru.penlk.presentation.mapping.orders.common;

import org.mapstruct.Mapper;
import ru.penlk.dao.entities.cars.Car;

@Mapper(componentModel = "spring")
public class CarIdMapper {
    public Long carToId(Car car) {
        return car.getId();
    }
}
