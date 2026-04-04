package ru.penlk.business.implementations.orders;

import lombok.AllArgsConstructor;
import ru.penlk.business.contracts.DomainValidationException;
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
import ru.penlk.dao.entities.users.clients.Client;
import ru.penlk.dao.entities.users.managers.Manager;
import ru.penlk.dao.entities.vo.Price;
import ru.penlk.dao.repositories.interfaces.cars.CarRepository;
import ru.penlk.dao.repositories.interfaces.cars.CarPartRepository;
import ru.penlk.dao.repositories.interfaces.orders.common.CommonOrderRepository;
import ru.penlk.dao.repositories.interfaces.users.clients.ClientRepository;
import ru.penlk.dao.repositories.interfaces.users.managers.ManagerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class CommonOrderServiceImpl implements CommonOrderService {
    private final CommonOrderRepository commonOrderRepository;
    private final ManagerRepository managerRepository;
    private final ClientRepository clientRepository;
    private final CarPartRepository carPartRepository;
    private final CarRepository carRepository;

    private final ManagerSelectionStrategy managerSelectionStrategy;
    private final CommonStateMapper commonStateMapper;
    private final RequiredNodeConfigurationService requiredNodeConfigurationService;

    @Override
    public CommonOrder create(CommonOrder request) {
        return commonOrderRepository.save(request);
    }

    @Override
    public CommonOrder read(Long orderId) throws ServiceException {
        Optional<CommonOrder> carOptional = commonOrderRepository.findById(orderId);

        if (carOptional.isPresent()) {
            return carOptional.get();
        }

        throw new ServiceException(String.format("CommonOrder with orderId: {%d} not found", orderId));
    }

    @Override
    public CommonOrder update(CommonOrder request) throws ServiceException {
        if (request.getId() == null) {
            throw new ServiceException("Order id must not be null");
        }

        CommonOrder order = commonOrderRepository.findById(request.getId())
                    .orElseThrow(() -> new ServiceException("Order not found"));

        order.setCar(request.getCar());
        order.setClient(request.getClient());
        order.setManager(request.getManager());
        order.setState(request.getState());

        return order;
    }

    @Override
    public void delete(Long orderId) throws ServiceException {
        commonOrderRepository.deleteById(orderId);
    }

    @Override
    public CommonOrder issue(Long clientId, Long carId) throws ServiceException, DomainValidationException {
        Optional<Client> clientOptional = clientRepository.findById(clientId);

        if (clientOptional.isEmpty()) {
            throw new ServiceException(String.format("Client with id: {%d} not found", clientId));
        }

        Optional<Car> carOptional = carRepository.findById(carId);

        if (carOptional.isEmpty()) {
            throw new ServiceException(String.format("Car with id: {%d} not found", carId));
        }

        requiredNodeConfigurationService.completeRequireNodes(carOptional.get(), new ArrayList<>());

        CommonOrder order = new CommonOrder(CommonOrderState.ISSUED, clientOptional.get(), null, carOptional.get());

        return commonOrderRepository.save(order);
    }

    @Override
    public CommonOrder confirm(Long orderId) throws ServiceException {
        Optional<Manager> optionalManager = managerSelectionStrategy.findManager(managerRepository.findAll());

        if (optionalManager.isEmpty()) {
            throw new ServiceException("Cannot find any manager");
        }

        CommonOrderFacade orderFacade = getFacade(orderId);

        if (orderFacade.tryConfirm(optionalManager.get()) == Boolean.FALSE) {
            throw new ServiceException(String.format("CommonOrder with id: {%d} impossible to confirm", orderId));
        }

        return commonOrderRepository.save(orderFacade.getOrder());
    }

    @Override
    public CommonOrder waitPurchase(Long orderId) throws ServiceException {
        CommonOrderFacade orderFacade = getFacade(orderId);

        if (orderFacade.tryWaitPurchase() == Boolean.FALSE) {
            throw new ServiceException(String.format("CommonOrder with id: {%d} impossible to waiting purchase", orderId));
        }

        return commonOrderRepository.save(orderFacade.getOrder());
    }

    @Override
    public CommonOrder purchase(Long orderId) throws ServiceException {
        CommonOrderFacade orderFacade = getFacade(orderId);

        if (orderFacade.tryPurchase() == Boolean.FALSE) {
            throw new ServiceException(String.format("CommonOrder with id: {%d} impossible to purchase", orderId));
        }

        return commonOrderRepository.save(orderFacade.getOrder());
    }

    @Override
    public CommonOrder carReadyToTake(Long orderId) throws ServiceException {
        CommonOrderFacade orderFacade = getFacade(orderId);

        if (orderFacade.tryCarReadyToTake() == Boolean.FALSE) {
            throw new ServiceException(String.format("CommonOrder with id: {%d} impossible to car take", orderId));
        }

        return commonOrderRepository.save(orderFacade.getOrder());
    }

    @Override
    public CommonOrder complete(Long orderId) throws ServiceException {
        CommonOrderFacade orderFacade = getFacade(orderId);

        if (orderFacade.tryComplete() == Boolean.FALSE) {
            throw new ServiceException(String.format("CommonOrder with id: {%d} impossible to complete", orderId));
        }

        return commonOrderRepository.save(orderFacade.getOrder());
    }

    @Override
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
