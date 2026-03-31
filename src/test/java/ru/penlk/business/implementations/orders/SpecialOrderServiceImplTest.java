package ru.penlk.business.implementations.orders;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.penlk.business.contracts.ServiceException;
import ru.penlk.business.contracts.orders.special.models.CreateSpecialOrderDto;
import ru.penlk.business.internal.CarPartConfigurationService;
import ru.penlk.business.internal.CarPartPriceCalculator;
import ru.penlk.business.internal.RequiredNodeConfigurationService;
import ru.penlk.dao.entities.cars.CarPart;
import ru.penlk.dao.entities.cars.Car;
import ru.penlk.dao.entities.users.clients.Client;
import ru.penlk.dao.entities.users.clients.ClientId;
import ru.penlk.dao.entities.vo.Price;
import ru.penlk.dao.repositories.interfaces.cars.CarRepository;
import ru.penlk.dao.repositories.interfaces.orders.special.SpecialOrderRepository;
import ru.penlk.dao.repositories.interfaces.orders.special.nodeset.NodeSetRepository;
import ru.penlk.dao.repositories.interfaces.users.clients.ClientRepository;
import ru.penlk.dao.repositories.interfaces.users.managers.ManagerRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SpecialOrderServiceImplTest {
    @Mock
    SpecialOrderRepository specialOrderRepository;

    @Mock
    ManagerRepository managerRepository;

    @Mock
    ClientRepository clientRepository;

    @Mock
    CarRepository carRepository;

    @Mock
    NodeSetRepository nodeSetRepository;

    @Mock
    CarPartConfigurationService carPartConfigurationService;

    @Mock
    RequiredNodeConfigurationService requiredNodeConfigurationService;

    @Mock
    CarPartPriceCalculator carPartPriceCalculator;

    @InjectMocks
    SpecialOrderServiceImpl service;

    @Test
    void create_carMissing_throws() {
        when(carRepository.findById(new CarId(10))).thenReturn(Optional.empty());

        var dto = new CreateSpecialOrderDto(ru.penlk.business.contracts.orders.special.models.SpecialOrderStateContract.Issued, 1L, 0L, 10L, List.of());

        assertThrows(ServiceException.class, () -> service.create(dto));
    }

    @Test
    void issue_success_createsAndReturns() {
        when(clientRepository.findById(new ClientId(1))).thenReturn(Optional.of(new Client(new ClientId(1))));
        when(carRepository.findById(new CarId(1))).thenReturn(Optional.of(new Car(new CarId(1), new Price(new BigDecimal("10")), null, null, null, null, null, null, null, null, null)));

        when(carPartConfigurationService.getCarParts(any(), any())).thenReturn(List.of(new CarPart(new CarPartId(1), "p", new NodeId(2))));
        when(requiredNodeConfigurationService.completeRequireNodes(any(), any())).thenReturn(List.of());
        when(carPartPriceCalculator.getSpecialCarPartsPrice(any(), any())).thenReturn(new Price(new BigDecimal("5")));
        when(specialOrderRepository.create(any())).thenAnswer(inv -> inv.getArgument(0));

        var dto = service.issue(1L, 1L, List.of());

        assertNotNull(dto);
    }
}
