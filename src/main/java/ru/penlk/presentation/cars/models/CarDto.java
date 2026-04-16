package ru.penlk.presentation.cars.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.NonNull;
import ru.penlk.dao.entities.cars.CarDrive;
import ru.penlk.dao.entities.cars.Fuel;
import ru.penlk.dao.entities.cars.GearShiftBox;

import java.math.BigDecimal;

public record CarDto(Long id,
                     @NonNull BigDecimal price,
                     @NotBlank String brand,
                     @NotBlank String model,
                     @NotBlank String body,
                     @NonNull Fuel fuel,
                     @NonNull BigDecimal enginePower,
                     @NonNull BigDecimal engineVolume,
                     @NonNull GearShiftBox gearShiftBox,
                     @NonNull CarDrive carDrive,
                     @Size(min = 3, max = 100) String colour) {
}
