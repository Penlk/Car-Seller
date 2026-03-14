package ru.penlk.business.implementations.orders;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.penlk.business.contracts.DomainValidationException;
import ru.penlk.business.contracts.ServiceException;
import ru.penlk.business.contracts.orders.common.models.CreateCommonOrderDto;
import ru.penlk.business.contracts.orders.common.models.CommonOrderDto;
import ru.penlk.dao.entities.cars.Car;
import ru.penlk.dao.entities.cars.CarId;
import ru.penlk.dao.entities.nodes.NodeId;
import ru.penlk.dao.entities.orders.commonOrder.CommonOrder;
import ru.penlk.dao.entities.orders.commonOrder.CommonOrderId;
import ru.penlk.dao.entities.orders.commonOrder.CommonOrderState;
import ru.penlk.dao.entities.users.clients.ClientId;
import ru.penlk.dao.entities.users.managers.ManagerId;
import ru.penlk.dao.entities.valueObjects.Price;
import ru.penlk.dao.repositories.interfaces.cars.CarRepository;
import ru.penlk.dao.repositories.interfaces.cars.parts.CarPartRepository;
import ru.penlk.dao.repositories.interfaces.nodes.require.RequireNodeRepository;
import ru.penlk.dao.repositories.interfaces.orders.common.CommonOrderRepository;
import ru.penlk.dao.repositories.interfaces.orders.common.configurations.CommonConfigurationRepository;
import ru.penlk.dao.repositories.interfaces.users.clients.ClientRepository;
import ru.penlk.dao.repositories.interfaces.users.managers.ManagerRepository;
import ru.penlk.business.implementations.orders.strategies.ManagerSelectionStrategy;
import ru.penlk.business.implementations.orders.states.mappers.CommonStateMapper;
import ru.penlk.business.implementations.orders.states.common.CommonOrderStateHandler;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommonOrderServiceImplTest {
    @Mock
    CommonOrderRepository commonOrderRepository;

    @Mock
    ManagerRepository managerRepository;

    @Mock
    ClientRepository clientRepository;

    @Mock
    RequireNodeRepository requireNodeRepository;

    @Mock
    CommonConfigurationRepository commonConfigurationRepository;

    @Mock
    CarPartRepository carPartRepository;

    @Mock
    CarRepository carRepository;

    @Mock
    ManagerSelectionStrategy managerSelectionStrategy;

    @Mock
    CommonStateMapper commonStateMapper;

    @InjectMocks
    CommonOrderServiceImpl service;

    @Test
    void create_mapsAndReturns() {
        CommonOrder order = new CommonOrder(CommonOrderId.defaultId(), CommonOrderState.ISSUED, new ClientId(1), ManagerId.defaultId(), new CarId(1));
        when(commonOrderRepository.create(any())).thenReturn(order);

        var dto = service.create(new CreateCommonOrderDto(ru.penlk.business.contracts.orders.common.models.CommonOrderStateContract.Issued,1,0,1));

        assertNotNull(dto);
        assertEquals(CommonOrderState.ISSUED, CommonOrderStateContractToState(dto.state()));
    }

    private CommonOrderState CommonOrderStateContractToState(ru.penlk.business.contracts.orders.common.models.CommonOrderStateContract c) {
        return ru.penlk.business.contracts.orders.common.models.CommonOrderStateContract.mapToCommonOrderState(c);
    }

    @Test
    void issue_clientMissing_throws() {
        when(clientRepository.findById(new ClientId(2))).thenReturn(Optional.empty());

        assertThrows(ServiceException.class, () -> service.issue(2L, 1L));
    }

    @Test
    void issue_missingNodes_throwsDomainValidation() {
        when(clientRepository.findById(new ClientId(1))).thenReturn(Optional.of(new ru.penlk.dao.entities.users.clients.Client(new ClientId(1))));
        when(carRepository.findById(new CarId(1))).thenReturn(Optional.of(new Car(new CarId(1), new Price(new BigDecimal("1")), null, null, null, null, null, null, null, null, null)));

        when(requireNodeRepository.findByCarId(new CarId(1))).thenReturn(List.of(new NodeId(1)));
        when(commonConfigurationRepository.findByCarId(new CarId(1))).thenReturn(List.of(new ru.penlk.dao.entities.carParts.CarPartId(5)));
        when(carPartRepository.query(any())).thenReturn(List.of(new ru.penlk.dao.entities.carParts.CarPart(new ru.penlk.dao.entities.carParts.CarPartId(5), "p", new NodeId(7))));

        assertThrows(DomainValidationException.class, () -> service.issue(1L, 1L));
    }

    @Test
    void confirm_noManager_throws() {
        when(managerSelectionStrategy.findManager(any())).thenReturn(Optional.empty());

        assertThrows(ServiceException.class, () -> service.confirm(1L));
    }
}
