package ru.penlk.presentation.mapping.cars.parts;

import org.mapstruct.Mapper;
import ru.penlk.dao.entities.cars.CarPart;
import ru.penlk.presentation.cars.parts.models.CarPartDto;
import ru.penlk.presentation.cars.parts.models.CreateCarPartDto;

@Mapper(componentModel = "spring")
public interface CreateCarPartMapper {
    CarPart createCarPartDtoToCarPart(CreateCarPartDto carPartDto);
}
