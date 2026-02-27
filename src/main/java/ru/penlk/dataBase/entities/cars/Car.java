package ru.penlk.dataBase.entities.cars;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Car {
    private CarId id;
    private CarPrice price;
    private String brand;
    private String model;
    private String body;
    private Fuel typeFuel;
    private EnginePower enginePower;
    private EngineVolume engineVolume;
    private GearShiftBox typeGearShiftBox;
    private CarDrive typeCareDrive;
    private String colour;
}




