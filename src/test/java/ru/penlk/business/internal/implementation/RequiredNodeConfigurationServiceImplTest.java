package ru.penlk.business.internal.implementation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.penlk.business.contracts.DomainValidationException;
import ru.penlk.dao.entities.carParts.CarPart;
import ru.penlk.dao.entities.carParts.CarPartId;
import ru.penlk.dao.entities.cars.Car;
import ru.penlk.dao.entities.cars.CarId;
import ru.penlk.dao.entities.nodes.NodeId;
import ru.penlk.dao.entities.valueObjects.Price;
import ru.penlk.dao.repositories.interfaces.cars.parts.CarPartRepository;
import ru.penlk.dao.repositories.interfaces.nodes.require.RequireNodeRepository;
import ru.penlk.dao.repositories.interfaces.orders.common.configurations.CommonConfigurationRepository;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RequiredNodeConfigurationServiceImplTest {
    @Mock
    CarPartRepository carPartRepository;

    @Mock
    CommonConfigurationRepository commonConfigurationRepository;

    @Mock
    RequireNodeRepository requireNodeRepository;

    @InjectMocks
    RequiredNodeConfigurationServiceImpl service;

    private Car makeCar() {
        return new Car(new CarId(1), new Price(BigDecimal.ZERO), null, null, null, null, null, null, null, null, null);
    }

    @Test
    void completeRequireNodes_success() {
        Car car = makeCar();

        // special parts contain node 1, so missing is node 2
        CarPart special = new CarPart(new CarPartId(1), "s", new NodeId(1));

        when(requireNodeRepository.findByCarId(car.getId())).thenReturn(List.of(new NodeId(1), new NodeId(2)));
        when(commonConfigurationRepository.findByCarId(car.getId())).thenReturn(List.of(new CarPartId(2)));
        CarPart completion = new CarPart(new CarPartId(2), "c", new NodeId(2));
        when(carPartRepository.findAll()).thenReturn(List.of(special, completion));

        var result = service.completeRequireNodes(car, List.of(special));

        assertEquals(1, result.size());
        assertEquals(new CarPartId(2), result.iterator().next().getId());
    }

    @Test
    void completeRequireNodes_missing_throwsDomainValidation() {
        Car car = makeCar();
        CarPart special = new CarPart(new CarPartId(1), "s", new NodeId(1));

        when(requireNodeRepository.findByCarId(car.getId())).thenReturn(List.of(new NodeId(1), new NodeId(2)));
        when(commonConfigurationRepository.findByCarId(car.getId())).thenReturn(List.of());
        when(carPartRepository.findAll()).thenReturn(List.of(special));

        assertThrows(DomainValidationException.class, () -> service.completeRequireNodes(car, List.of(special)));
    }
}
