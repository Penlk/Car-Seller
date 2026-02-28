package ru.penlk.dataBase.repositories.interfaces.cars;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.penlk.dataBase.entities.cars.*;
import ru.penlk.dataBase.entities.valueObjects.Price;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarQuery {
    private Price minPrice;
    private Price maxPrice;
    private List<String> brands;
    private String model;
    private List<String> bodies;
    private List<Fuel> fuels;
    private EnginePower minEnginePower;
    private EnginePower maxEnginePower;
    private EngineVolume minEngineVolume;
    private EngineVolume maxEngineVolume;
    private List<GearShiftBox> gearShiftBoxes;
    private List<CarDrive> carDrives;
    private List<String> colours;
}