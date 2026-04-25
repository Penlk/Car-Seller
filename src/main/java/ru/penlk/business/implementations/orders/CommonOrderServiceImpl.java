package ru.penlk.business.implementations.orders;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.penlk.business.contracts.DomainValidationException;
import ru.penlk.business.contracts.KeycloakAdminService;
import ru.penlk.business.contracts.ServiceException;
import ru.penlk.business.contracts.orders.CommonOrderService;
import ru.penlk.business.implementations.orders.states.common.CommonOrderCore;
import ru.penlk.business.implementations.orders.states.common.CommonOrderFacade;
import ru.penlk.business.implementations.orders.states.common.CommonOrderStateHandler;
import ru.penlk.business.implementations.orders.states.mappers.CommonStateMapper;
import ru.penlk.business.implementations.orders.strategies.ManagerSelectionStrategy;
import ru.penlk.business.internal.RequiredNodeConfigurationService;
import ru.penlk.dao.entities.cars.Car;
import ru.penlk.dao.entities.orders.common.CommonOrder;
import ru.penlk.dao.entities.orders.common.CommonOrderState;
import ru.penlk.dao.repositories.interfaces.cars.CarRepository;
import ru.penlk.dao.repositories.interfaces.orders.common.CommonOrderRepository;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@Service
@Transactional
public class CommonOrderServiceImpl implements CommonOrderService {
    private final CommonOrderRepository commonOrderRepository;
    private final CarRepository carRepository;
    private final KeycloakAdminService keycloakAdminService;

    private final ManagerSelectionStrategy managerSelectionStrategy;
    private final CommonStateMapper commonStateMapper;
    private final RequiredNodeConfigurationService requiredNodeConfigurationService;

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER') or hasRole('USER') and @orderSecurityImpl.isOwnerCommonOrder(#orderId)")
    public CommonOrder find(Long orderId) throws ServiceException {
        Optional<CommonOrder> carOptional = commonOrderRepository.findById(orderId);

        if (carOptional.isPresent()) {
            return carOptional.get();
        }

        throw new ServiceException(String.format("CommonOrder with id: {%d} not found", orderId));
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER') or hasRole('USER') and @orderSecurityImpl.isOwnerCommonOrder(#orderId)")
    public void delete(Long orderId) throws ServiceException {
        commonOrderRepository.deleteById(orderId);
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    public CommonOrder placement(Long carId) throws ServiceException, DomainValidationException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null) {
            throw new ServiceException("Authentication required");
        }

        String userId = auth.getName();

        Optional<String> optionalManager = managerSelectionStrategy.findManager(keycloakAdminService.getManagers());

        if (optionalManager.isEmpty()) {
            throw new ServiceException("Cannot find any manager");
        }

        Optional<Car> carOptional = carRepository.findById(carId);

        if (carOptional.isEmpty()) {
            throw new ServiceException(String.format("Car with id: {%d} not found", carId));
        }

        requiredNodeConfigurationService.completeRequireNodes(carOptional.get(), new ArrayList<>());

        CommonOrder order = new CommonOrder(CommonOrderState.PLACED, userId, optionalManager.get(), carOptional.get());

        return commonOrderRepository.save(order);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public CommonOrder confirm(Long orderId) throws ServiceException {
        CommonOrderFacade orderFacade = getFacade(orderId);

        if (orderFacade.tryConfirm() == Boolean.FALSE) {
            throw new ServiceException(String.format("CommonOrder with id: {%d} impossible to confirm", orderId));
        }

        return commonOrderRepository.save(orderFacade.getOrder());
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public CommonOrder waitPurchase(Long orderId) throws ServiceException {
        CommonOrderFacade orderFacade = getFacade(orderId);

        if (orderFacade.tryWaitPurchase() == Boolean.FALSE) {
            throw new ServiceException(String.format("CommonOrder with id: {%d} impossible to waiting purchase", orderId));
        }

        return commonOrderRepository.save(orderFacade.getOrder());
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'USER')")
    public CommonOrder purchase(Long orderId) throws ServiceException {
        CommonOrderFacade orderFacade = getFacade(orderId);

        if (orderFacade.tryPurchase() == Boolean.FALSE) {
            throw new ServiceException(String.format("CommonOrder with id: {%d} impossible to purchase", orderId));
        }

        return commonOrderRepository.save(orderFacade.getOrder());
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public CommonOrder carReadyToTake(Long orderId) throws ServiceException {
        CommonOrderFacade orderFacade = getFacade(orderId);

        if (orderFacade.tryCarReadyToTake() == Boolean.FALSE) {
            throw new ServiceException(String.format("CommonOrder with id: {%d} impossible to car take", orderId));
        }

        return commonOrderRepository.save(orderFacade.getOrder());
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public CommonOrder complete(Long orderId) throws ServiceException {
        CommonOrderFacade orderFacade = getFacade(orderId);

        if (orderFacade.tryComplete() == Boolean.FALSE) {
            throw new ServiceException(String.format("CommonOrder with id: {%d} impossible to complete", orderId));
        }

        return commonOrderRepository.save(orderFacade.getOrder());
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'USER')")
    public CommonOrder cancel(Long orderId) throws ServiceException {
        CommonOrderFacade orderFacade = getFacade(orderId);

        if (orderFacade.tryCancel() == Boolean.FALSE) {
            throw new ServiceException(String.format("CommonOrder with id: {%d} cancelled", orderId));
        }

        return commonOrderRepository.save(orderFacade.getOrder());
    }

    private CommonOrderFacade getFacade(Long orderId) {
        Optional<CommonOrder> optionalOrder = commonOrderRepository.findById(orderId);

        if (optionalOrder.isEmpty()) {
            throw new ServiceException(String.format("CommonOrder with id: {%d} not found", orderId));
        }

        CommonOrder order = optionalOrder.get();
        CommonOrderStateHandler state = commonStateMapper.map(order.getState());

        return new CommonOrderFacade(new CommonOrderCore(order, state));
    }
}
