package ru.penlk.dataAcessLayer.entities.cars;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.penlk.dataAcessLayer.entities.valueObjects.Price;

@AllArgsConstructor
@Getter
@Setter
public class Car {
    private CarId id;
    private Price price;
    private String brand;
    private String model;
    private String body;
    private Fuel fuel;
    private EnginePower enginePower;
    private EngineVolume engineVolume;
    private GearShiftBox gearShiftBox;
    private CarDrive carDrive;
    private String colour;
}




