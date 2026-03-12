package ru.penlk.businessLayer.implementations.orders;

import lombok.AllArgsConstructor;
import ru.penlk.businessLayer.contracts.DomainValidationException;
import ru.penlk.businessLayer.contracts.ServiceException;
import ru.penlk.businessLayer.contracts.commonOrders.CommonOrderService;
import ru.penlk.businessLayer.contracts.commonOrders.models.CommonOrderDto;
import ru.penlk.businessLayer.contracts.commonOrders.models.CreateCommonOrderDto;
import ru.penlk.businessLayer.contracts.commonOrders.models.IssueCommonOrderDto;
import ru.penlk.businessLayer.implementations.orders.states.common.CommonOrderCore;
import ru.penlk.businessLayer.implementations.orders.states.common.CommonOrderFacade;
import ru.penlk.businessLayer.implementations.orders.states.common.CommonOrderStateHandler;
import ru.penlk.businessLayer.implementations.orders.states.mappers.CommonStateMapper;
import ru.penlk.businessLayer.implementations.orders.strategies.ManagerSelectionStrategy;
import ru.penlk.dataAcessLayer.entities.carParts.CarPart;
import ru.penlk.dataAcessLayer.entities.carParts.CarPartId;
import ru.penlk.dataAcessLayer.entities.cars.Car;
import ru.penlk.dataAcessLayer.entities.cars.CarId;
import ru.penlk.dataAcessLayer.entities.nodes.NodeId;
import ru.penlk.dataAcessLayer.entities.orders.commonOrder.CommonOrder;
import ru.penlk.dataAcessLayer.entities.orders.commonOrder.CommonOrderId;
import ru.penlk.dataAcessLayer.entities.orders.commonOrder.CommonOrderState;
import ru.penlk.dataAcessLayer.entities.users.clients.ClientId;
import ru.penlk.dataAcessLayer.entities.users.managers.Manager;
import ru.penlk.dataAcessLayer.entities.users.managers.ManagerId;
import ru.penlk.dataAcessLayer.entities.valueObjects.Price;
import ru.penlk.dataAcessLayer.repositories.interfaces.carParts.CarPartRepository;
import ru.penlk.dataAcessLayer.repositories.interfaces.cars.CarRepository;
import ru.penlk.dataAcessLayer.repositories.interfaces.orders.commonConfigurations.CommonConfigurationRepository;
import ru.penlk.dataAcessLayer.repositories.interfaces.orders.commonOrders.CommonOrderNotFoundException;
import ru.penlk.dataAcessLayer.repositories.interfaces.orders.commonOrders.CommonOrderRepository;
import ru.penlk.dataAcessLayer.repositories.interfaces.requireNodes.RequireNodeRepository;
import ru.penlk.dataAcessLayer.repositories.interfaces.users.clients.ClientRepository;
import ru.penlk.dataAcessLayer.repositories.interfaces.users.managers.ManagerRepository;

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

        List<CarPart> carParts = carPartRepository.findAll().stream()
                .filter(x -> carPartIds.contains(x.getId()))
                .toList();

        List<NodeId> nodeIds = carParts.stream()
                .map(CarPart::getNodeId)
                .toList();

        List<NodeId> missingNodeIds = nodeIds.stream().filter(x -> !requireNodeIds.contains(x)).toList();

        if (!missingNodeIds.isEmpty()) {
            StringBuilder missingNodeIdsString = new StringBuilder("Missing required nodes: [\n");

            missingNodeIds.forEach(missingNodeId -> {
                missingNodeIdsString.append(String.format("%s,\n", missingNodeId));
            });

            missingNodeIdsString.append("]\n");

            throw new DomainValidationException(missingNodeIdsString.toString());
        }

        Price price = carOptional.get().getPrice();

        return IssueCommonOrderDto.mapToDto(
                commonOrderRepository.create(new CommonOrder(
                                CommonOrderId.defaultId(),
                                CommonOrderState.Issued,
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
