package ru.penlk.presentation.mapping.cars.parts;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.penlk.dao.entities.cars.CarPart;
import ru.penlk.presentation.cars.parts.models.CarPartDto;

@Mapper(componentModel = "spring", uses = NodeMapperId.class)
public interface CarPartMapper {
    CarPart carPartDtoToCarPart(CarPartDto carPartDto);

    @Mapping(source = "node", target = "nodeId")
    CarPartDto carPartToCarPartDto(CarPart carPart);
}
