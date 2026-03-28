package ru.penlk.dao.entities.cars;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.penlk.dao.entities.BaseEntity;
import ru.penlk.dao.entities.valueObjects.Price;

@AllArgsConstructor
@Getter
@Setter
@Entity
@NoArgsConstructor
public class Car extends BaseEntity {
    @Column
    private Price price;

    @Column
    private String brand;

    @Column
    private String model;

    @Column
    private String body;

    @Column
    private Fuel fuel;

    @Column
    private EnginePower enginePower;

    @Column
    private EngineVolume engineVolume;

    @Column
    private GearShiftBox gearShiftBox;

    @Column
    private CarDrive carDrive;

    @Column
    private String colour;
}




