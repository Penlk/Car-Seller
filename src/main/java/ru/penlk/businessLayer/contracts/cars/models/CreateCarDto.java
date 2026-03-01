package ru.penlk.businessLayer.contracts.cars.models;

import ru.penlk.dataAcessLayer.entities.cars.Car;
import ru.penlk.dataAcessLayer.entities.cars.CarId;
import ru.penlk.dataAcessLayer.entities.cars.EnginePower;
import ru.penlk.dataAcessLayer.entities.cars.EngineVolume;
import ru.penlk.dataAcessLayer.entities.valueObjects.Price;

import java.math.BigDecimal;

public record CreateCarDto(BigDecimal price,
                           String brand,
                           String model,
                           String body,
                           FuelContract fuel,
                           BigDecimal enginePower,
                           BigDecimal engineVolume,
                           GearShiftBoxContract gearShiftBox,
                           CarDriveContract CarDrive,
                           String colour) {

    public static Car MapToCar(CreateCarDto createCarDto) {
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
