package ru.penlk.businessLayer.contracts.cars.models;

import java.math.BigDecimal;

public record CarDto(long id,
                     BigDecimal price,
                     String brand,
                     String model,
                     String body,
                     String fuel,
                     BigDecimal enginePower,
                     BigDecimal engineVolume,
                     String gearShiftBox,
                     String CarDrive,
                     String colour) {

}
