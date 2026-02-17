package ru.penlk.dataBase.cars;

import ru.penlk.dataBase.engines.EngineId;
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
    private EngineId engineId;
    private GearShiftBox typeGearShiftBox;
    private CarDrive typeCareDrive;
    private String colour;
}
