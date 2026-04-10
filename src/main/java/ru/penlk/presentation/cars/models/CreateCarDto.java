package ru.penlk.presentation.cars.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import ru.penlk.dao.entities.cars.Car;
import ru.penlk.dao.entities.cars.CarDrive;
import ru.penlk.dao.entities.cars.Fuel;
import ru.penlk.dao.entities.cars.GearShiftBox;
import ru.penlk.dao.entities.vo.EnginePower;
import ru.penlk.dao.entities.vo.EngineVolume;
import ru.penlk.dao.entities.vo.Price;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateCarDto {
    @NonNull BigDecimal price;

    @NotBlank String brand;

    @NotBlank String model;

    @NotBlank String body;

    @NonNull Fuel fuel;

    @NonNull BigDecimal enginePower;

    @NonNull BigDecimal engineVolume;

    @NonNull GearShiftBox gearShiftBox;

    @NonNull CarDrive carDrive;

    @Size(min = 3, max = 100) String colour;
}
