package ru.penlk.businessLayer.contracts.cars.models;

import ru.penlk.dao.entities.cars.Car;
import ru.penlk.dao.entities.cars.CarId;
import ru.penlk.dao.entities.cars.EnginePower;
import ru.penlk.dao.entities.cars.EngineVolume;
import ru.penlk.dao.entities.valueObjects.Price;

import java.math.BigDecimal;

public record CreateCarDto(BigDecimal price,
                           String brand,
                           String model,
                           String body,
                           FuelContract fuel,
                           BigDecimal enginePower,
                           BigDecimal engineVolume,
                           GearShiftBoxContract gearShiftBox,
                           CarDriveContract carDrive,
                           String colour) {

    public static Car mapToModel(CreateCarDto createCarDto) {
        return new Car(
                CarId.defaultId(),
                new Price(createCarDto.price()),
                createCarDto.brand(),
                createCarDto.model(),
                createCarDto.body(),
                FuelContract.mapToFuel(createCarDto.fuel()),
                new EnginePower(createCarDto.enginePower()),
                new EngineVolume(createCarDto.engineVolume()),
                GearShiftBoxContract.mapToGearShiftBox(createCarDto.gearShiftBox()),
                CarDriveContract.mapToCarDrive(createCarDto.carDrive()),
                createCarDto.colour()
        );
    }
}
