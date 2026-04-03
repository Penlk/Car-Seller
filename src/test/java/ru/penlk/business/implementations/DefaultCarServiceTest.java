package ru.penlk.business.implementations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.penlk.business.contracts.ServiceException;
import ru.penlk.business.contracts.cars.models.CarDto;
import ru.penlk.business.contracts.cars.models.CreateCarDto;
import ru.penlk.business.contracts.cars.models.FuelContract;
import ru.penlk.business.contracts.cars.models.GearShiftBoxContract;
import ru.penlk.business.contracts.cars.models.CarDriveContract;
import ru.penlk.dao.repositories.interfaces.cars.CarRepository;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class DefaultCarServiceTest {
    @Mock
    CarRepository carRepository;

    @InjectMocks
    CarServiceImpl service;

    private CreateCarDto makeCreate() {
        return new CreateCarDto(new BigDecimal("1"), "b", "m", "body", FuelContract.PETROL, new BigDecimal("1"), new BigDecimal("1"), GearShiftBoxContract.MANUAL, CarDriveContract.FRONT, "c");
    }

    @Test
    void create_mapsAndReturns() {
        when(carRepository.create(any())).thenAnswer(inv -> inv.getArgument(0));

        var dto = service.create(makeCreate());

        assertEquals("b", dto.brand());
    }

    @Test
    void read_notFound_throws() {
        when(carRepository.findById(new CarId(5))).thenReturn(Optional.empty());

        assertThrows(ServiceException.class, () -> service.read(5L));
    }

    @Test
    void update_notFound_throwsServiceException() {
        doThrow(new CarNotFoundException(new CarId(6))).when(carRepository).update(any());

        CarDto req = new CarDto(6L, new BigDecimal("1"), "brand", "model", "body", FuelContract.PETROL, new BigDecimal("1"), new BigDecimal("1"), GearShiftBoxContract.MANUAL, CarDriveContract.FRONT, "colour");

        assertThrows(ServiceException.class, () -> service.update(req));
    }
}