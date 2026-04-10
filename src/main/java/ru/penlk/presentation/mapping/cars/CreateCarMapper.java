package ru.penlk.presentation.mapping.cars;

import org.mapstruct.Mapper;
import ru.penlk.dao.entities.cars.Car;
import ru.penlk.presentation.cars.models.CreateCarDto;
import ru.penlk.presentation.mapping.vo.EnginePowerMapper;
import ru.penlk.presentation.mapping.vo.EngineVolumeMapper;
import ru.penlk.presentation.mapping.vo.PriceMapper;

@Mapper(componentModel = "spring", uses = {
        EngineVolumeMapper.class,
        EnginePowerMapper.class,
        PriceMapper.class
})
public interface CreateCarMapper {
    Car createCarDtoToCar(CreateCarDto carDto);
}
