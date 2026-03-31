package ru.penlk.business.internal.implementation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.penlk.business.contracts.IncompatibleComponentException;
import ru.penlk.business.contracts.ServiceException;
import ru.penlk.dao.entities.carParts.CarPart;
import ru.penlk.dao.entities.carParts.CarPartId;
import ru.penlk.dao.entities.cars.Car;
import ru.penlk.dao.entities.cars.CarId;
import ru.penlk.dao.entities.nodes.NodeId;
import ru.penlk.dao.entities.orders.specialAllowedParts.SpecialAllowedPart;
import ru.penlk.dao.entities.valueObjects.Price;
import ru.penlk.dao.repositories.interfaces.cars.parts.CarPartNotFoundException;
import ru.penlk.dao.repositories.interfaces.cars.parts.CarPartRepository;
import ru.penlk.dao.repositories.interfaces.orders.special.configurators.SpecialConfigurationRepository;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarPartConfigurationServiceImplTest {
    @Mock
    CarPartRepository carPartRepository;

    @Mock
    SpecialConfigurationRepository specialConfigurationRepository;

    @InjectMocks
    CarPartConfigurationServiceImpl service;

    private Car makeCar() {
        return new Car(new CarId(1), new Price(BigDecimal.ZERO), null, null, null, null, null, null, null, null, null);
    }

    @Test
    void getCarParts_success() {
        Car car = makeCar();
        CarPart p1 = new CarPart(new CarPartId(1), "a", new NodeId(1));
        CarPart p2 = new CarPart(new CarPartId(2), "b", new NodeId(2));

        when(carPartRepository.query(any())).thenReturn(List.of(p1, p2));
        when(specialConfigurationRepository.findByCarId(car.getId()))
                .thenReturn(List.of(
                        new SpecialAllowedPart(car.getId(), new CarPartId(1), new Price(new BigDecimal("1"))),
                        new SpecialAllowedPart(car.getId(), new CarPartId(2), new Price(new BigDecimal("2")))
                ));

        var result = service.getCarParts(car, List.of(1L, 2L));

        assertEquals(2, result.size());
    }

    @Test
    void getCarParts_notFound_throwsServiceException() {
        when(carPartRepository.query(any())).thenThrow(new CarPartNotFoundException(new CarPartId(1)));

        assertThrows(ServiceException.class, () -> service.getCarParts(makeCar(), List.of(1L)));
    }

    @Test
    void getCarParts_incompatible_throwsIncompatible() {
        Car car = makeCar();
        CarPart p1 = new CarPart(new CarPartId(1), "a", new NodeId(1));

        when(carPartRepository.query(any())).thenReturn(List.of(p1));
        when(specialConfigurationRepository.findByCarId(car.getId())).thenReturn(List.of());

        assertThrows(IncompatibleComponentException.class, () -> service.getCarParts(car, List.of(1L)));
    }

    @Test
    void getCarParts_duplicateNodeIds_throwsServiceException() {
        Car car = makeCar();
        CarPart p1 = new CarPart(new CarPartId(1), "a", new NodeId(1));
        CarPart p2 = new CarPart(new CarPartId(2), "b", new NodeId(1));

        when(carPartRepository.query(any())).thenReturn(List.of(p1, p2));
        when(specialConfigurationRepository.findByCarId(car.getId()))
                .thenReturn(List.of(
                        new SpecialAllowedPart(car.getId(), new CarPartId(1), new Price(new BigDecimal("1"))),
                        new SpecialAllowedPart(car.getId(), new CarPartId(2), new Price(new BigDecimal("2")))
                ));

        assertThrows(ServiceException.class, () -> service.getCarParts(car, List.of(1L, 2L)));
    }
}
