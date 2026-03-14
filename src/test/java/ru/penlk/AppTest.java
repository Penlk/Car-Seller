package ru.penlk;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import ru.penlk.business.contracts.cars.models.CarDriveContract;
import ru.penlk.business.contracts.cars.models.CreateCarDto;
import ru.penlk.business.contracts.cars.models.FuelContract;
import ru.penlk.business.contracts.cars.models.GearShiftBoxContract;
import ru.penlk.dao.repositories.interfaces.cars.CarRepository;
import ru.penlk.dao.repositories.interfaces.cars.parts.CarPartRepository;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class AppTest {

    @Test
    void shouldComputeSum() {
        CarRepository carRepository = Mockito.mock(CarRepository.class);

        CreateCarDto createCarDto = new CreateCarDto(
                new BigDecimal(123),
                "carich",
                "777",
                "x3",
                FuelContract.ELECTRICITY,
                new BigDecimal(123),
                new BigDecimal(123),
                GearShiftBoxContract.MANUAL,
                CarDriveContract.FULL,
                "purple"
        );

//        when(carRepository.create())
    }
}
