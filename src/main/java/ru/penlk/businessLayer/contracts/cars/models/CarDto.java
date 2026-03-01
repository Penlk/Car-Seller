package ru.penlk.businessLayer.contracts.cars.models;

import ru.penlk.dataAcessLayer.entities.cars.Car;
import ru.penlk.dataAcessLayer.entities.cars.CarId;
import ru.penlk.dataAcessLayer.entities.cars.EnginePower;
import ru.penlk.dataAcessLayer.entities.cars.EngineVolume;
import ru.penlk.dataAcessLayer.entities.valueObjects.Price;

import java.math.BigDecimal;

public record CarDto(long id,
                     BigDecimal price,
                     String brand,
                     String model,
                     String body,
                     FuelContract fuel,
                     BigDecimal enginePower,
                     BigDecimal engineVolume,
                     GearShiftBoxContract gearShiftBox,
                     CarDriveContract carDrive,
                     String colour) {

    public static CarDto MapToDto(Car car) {
        return new CarDto(
                car.getId().id(),
                car.getPrice().value(),
                car.getBrand(),
                car.getModel(),
                car.getBody(),
                FuelContract.mapToContract(car.getFuel()),
                car.getEnginePower().value(),
                car.getEngineVolume().value(),
                GearShiftBoxContract.mapToContract(car.getGearShiftBox()),
                CarDriveContract.mapToContract(car.getCarDrive()),
                car.getColour()
        );
    }

    public static Car MapToCar(CarDto carDto) {
        return new Car(
                new CarId(carDto.id()),
                new Price(carDto.price()),
                carDto.brand(),
                carDto.model(),
                carDto.body(),
                FuelContract.mapToFuel(carDto.fuel()),
                new EnginePower(carDto.enginePower()),
                new EngineVolume(carDto.engineVolume()),
                GearShiftBoxContract.mapToGearShiftBox(carDto.gearShiftBox()),
                CarDriveContract.mapToCarDrive(carDto.carDrive()),
                carDto.colour()
        );
    }
}
