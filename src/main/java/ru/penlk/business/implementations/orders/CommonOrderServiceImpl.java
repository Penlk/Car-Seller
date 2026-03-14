package ru.penlk.business.implementations.orders;

import lombok.AllArgsConstructor;
import ru.penlk.business.contracts.DomainValidationException;
import ru.penlk.business.contracts.ServiceException;
import ru.penlk.business.contracts.orders.common.CommonOrderService;
import ru.penlk.business.contracts.orders.common.models.CommonOrderDto;
import ru.penlk.business.contracts.orders.common.models.CreateCommonOrderDto;
import ru.penlk.business.contracts.orders.common.models.IssueCommonOrderDto;
import ru.penlk.business.implementations.orders.states.common.CommonOrderCore;
import ru.penlk.business.implementations.orders.states.common.CommonOrderFacade;
import ru.penlk.business.implementations.orders.states.common.CommonOrderStateHandler;
import ru.penlk.business.implementations.orders.states.mappers.CommonStateMapper;
import ru.penlk.business.implementations.orders.strategies.ManagerSelectionStrategy;
import ru.penlk.dao.entities.carParts.CarPart;
import ru.penlk.dao.entities.carParts.CarPartId;
import ru.penlk.dao.entities.cars.Car;
import ru.penlk.dao.entities.cars.CarId;
import ru.penlk.dao.entities.nodes.NodeId;
import ru.penlk.dao.entities.orders.commonOrder.CommonOrder;
import ru.penlk.dao.entities.orders.commonOrder.CommonOrderId;
import ru.penlk.dao.entities.orders.commonOrder.CommonOrderState;
import ru.penlk.dao.entities.users.clients.ClientId;
import ru.penlk.dao.entities.users.managers.Manager;
import ru.penlk.dao.entities.users.managers.ManagerId;
import ru.penlk.dao.entities.valueObjects.Price;
import ru.penlk.dao.repositories.interfaces.cars.CarRepository;
import ru.penlk.dao.repositories.interfaces.cars.parts.CarPartNotFoundException;
import ru.penlk.dao.repositories.interfaces.cars.parts.CarPartRepository;
import ru.penlk.dao.repositories.interfaces.nodes.require.RequireNodeRepository;
import ru.penlk.dao.repositories.interfaces.orders.common.CommonOrderNotFoundException;
import ru.penlk.dao.repositories.interfaces.orders.common.CommonOrderRepository;
import ru.penlk.dao.repositories.interfaces.orders.common.configurations.CommonConfigurationRepository;
import ru.penlk.dao.repositories.interfaces.users.clients.ClientRepository;
import ru.penlk.dao.repositories.interfaces.users.managers.ManagerRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class CommonOrderServiceImpl implements CommonOrderService {
    private final CommonOrderRepository commonOrderRepository;
    private final ManagerRepository managerRepository;
    private final ClientRepository clientRepository;
    private final RequireNodeRepository requireNodeRepository;
    private final CommonConfigurationRepository commonConfigurationRepository;
    private final CarPartRepository carPartRepository;
    private final CarRepository carRepository;

    private final ManagerSelectionStrategy managerSelectionStrategy;
    private final CommonStateMapper commonStateMapper;

    @Override
    public CommonOrderDto create(CreateCommonOrderDto request) {
        CommonOrder car = commonOrderRepository.create(CreateCommonOrderDto.mapToModel(request));

        return CommonOrderDto.mapToDto(car);
    }

    @Override
    public CommonOrderDto read(Long id) throws ServiceException {
        Optional<CommonOrder> carOptional = commonOrderRepository.findById(new CommonOrderId(id));

        if (carOptional.isPresent()) {
            return CommonOrderDto.mapToDto(carOptional.get());
        }

        throw new ServiceException(String.format("CommonOrder with id: {%d} not found", id));
    }

    @Override
    public CommonOrderDto update(CommonOrderDto request) throws ServiceException {
        try {
            CommonOrder mappingCommonOrder = CommonOrderDto.mapToModel(request);

            return CommonOrderDto.mapToDto(
                    commonOrderRepository.update(mappingCommonOrder)
            );
        } catch (CommonOrderNotFoundException e) {
            throw new ServiceException(String.format("CommonOrder with id: {%d} not found", e.getId().id()));
        }
    }

    @Override
    public void delete(Long id) throws ServiceException {
        try {
            commonOrderRepository.delete(new CommonOrderId(id));
        } catch (CommonOrderNotFoundException e) {
            throw new ServiceException(String.format("CommonOrder with id: {%d} not found", id));
        }
    }

    @Override
    public IssueCommonOrderDto issue(Long clientId, Long carId) throws ServiceException, DomainValidationException {
        if (clientRepository.findById(new ClientId(clientId)).isEmpty()) {
            throw new ServiceException(String.format("Client with id: {%d} not found", clientId));
        }

        Optional<Car> carOptional = carRepository.findById(new CarId(carId));

        if (carOptional.isEmpty()) {
            throw new ServiceException(String.format("Car with id: {%d} not found", carId));
        }

        Collection<NodeId> requireNodeIds = requireNodeRepository.findByCarId(new CarId(carId));

        Collection<CarPartId> carPartIds = commonConfigurationRepository.findByCarId(new CarId(carId));

        Collection<CarPart> carParts;

        try {
            carParts = carPartRepository.query(carPartIds);
        } catch (CarPartNotFoundException e) {
            throw new ServiceException(e.getMessage());
        }

        List<NodeId> nodeIds = carParts.stream()
                .map(CarPart::getNodeId)
                .toList();

        List<NodeId> missingNodeIds = nodeIds.stream().filter(x -> !requireNodeIds.contains(x)).toList();

        if (!missingNodeIds.isEmpty()) {
            throw new DomainValidationException("Missing required nodes: " + missingNodeIds);
        }

        Price price = carOptional.get().getPrice();

        return IssueCommonOrderDto.mapToDto(
                commonOrderRepository.create(new CommonOrder(
                                CommonOrderId.defaultId(),
                                CommonOrderState.ISSUED,
                                new ClientId(clientId),
                                ManagerId.defaultId(),
                                new CarId(carId)
                        )
                ),
                price
        );
    }

    @Override
    public CommonOrderDto confirm(Long orderId) throws ServiceException {
        Optional<Manager> optionalManager = managerSelectionStrategy.findManager(managerRepository.findAll());

        if (optionalManager.isEmpty()) {
            throw new ServiceException("Cannot find any manager");
        }

        CommonOrderFacade orderFacade = getFacade(orderId);

        if (orderFacade.tryConfirm(optionalManager.get().getId()) == Boolean.FALSE) {
            throw new ServiceException(String.format("CommonOrder with id: {%d} impossible to confirm", orderId));
        }

        return CommonOrderDto.mapToDto(orderFacade.getOrder());
    }

    @Override
    public CommonOrderDto waitPurchase(Long orderId) throws ServiceException {
        CommonOrderFacade orderFacade = getFacade(orderId);

        if (orderFacade.tryWaitPurchase() == Boolean.FALSE) {
            throw new ServiceException(String.format("CommonOrder with id: {%d} impossible to waiting purchase", orderId));
        }

        return CommonOrderDto.mapToDto(orderFacade.getOrder());
    }

    @Override
    public CommonOrderDto purchase(Long orderId) throws ServiceException {
        CommonOrderFacade orderFacade = getFacade(orderId);

        if (orderFacade.tryPurchase() == Boolean.FALSE) {
            throw new ServiceException(String.format("CommonOrder with id: {%d} impossible to purchase", orderId));
        }

        return CommonOrderDto.mapToDto(orderFacade.getOrder());
    }

    @Override
    public CommonOrderDto carReadyToTake(Long orderId) throws ServiceException {
        CommonOrderFacade orderFacade = getFacade(orderId);

        if (orderFacade.tryCarReadyToTake() == Boolean.FALSE) {
            throw new ServiceException(String.format("CommonOrder with id: {%d} impossible to car take", orderId));
        }

        return CommonOrderDto.mapToDto(orderFacade.getOrder());
    }

    @Override
    public CommonOrderDto complete(Long orderId) throws ServiceException {
        CommonOrderFacade orderFacade = getFacade(orderId);

        if (orderFacade.tryComplete() == Boolean.FALSE) {
            throw new ServiceException(String.format("CommonOrder with id: {%d} impossible to complete", orderId));
        }

        return CommonOrderDto.mapToDto(orderFacade.getOrder());
    }

    @Override
    public void cancel(Long orderId) throws ServiceException {
        CommonOrderFacade orderFacade = getFacade(orderId);

        if (orderFacade.tryCancel() == Boolean.FALSE) {
            throw new ServiceException(String.format("CommonOrder with id: {%d} cancelled", orderId));
        }
    }

    private CommonOrderFacade getFacade(Long orderId) {
        Optional<CommonOrder> optionalOrder = commonOrderRepository.findById(new CommonOrderId(orderId));

        if (optionalOrder.isEmpty()) {
            throw new ServiceException(String.format("CommonOrder with id: {%d} not found", orderId));
        }

        CommonOrder order = optionalOrder.get();
        CommonOrderStateHandler state = commonStateMapper.map(order.getState());

        return new CommonOrderFacade(new CommonOrderCore(order, state));
    }
}
