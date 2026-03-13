package ru.penlk.businessLayer.implementations.orders;

import lombok.AllArgsConstructor;
import ru.penlk.businessLayer.contracts.ServiceException;
import ru.penlk.businessLayer.contracts.specialOrders.SpecialOrderService;
import ru.penlk.businessLayer.contracts.specialOrders.models.CreateSpecialOrderDto;
import ru.penlk.businessLayer.contracts.specialOrders.models.IssueSpecialOrderDto;
import ru.penlk.businessLayer.contracts.specialOrders.models.SpecialOrderDto;
import ru.penlk.businessLayer.implementations.orders.states.mappers.SpecialStateMapper;
import ru.penlk.businessLayer.implementations.orders.states.special.SpecialOrderCore;
import ru.penlk.businessLayer.implementations.orders.states.special.SpecialOrderFacade;
import ru.penlk.businessLayer.implementations.orders.states.special.SpecialOrderStateHandler;
import ru.penlk.businessLayer.implementations.orders.strategies.ManagerSelectionStrategy;
import ru.penlk.businessLayer.internal.CarPartConfigurationService;
import ru.penlk.businessLayer.internal.CarPartPriceCalculator;
import ru.penlk.businessLayer.internal.RequiredNodeConfigurationService;
import ru.penlk.dataAcessLayer.entities.carParts.CarPart;
import ru.penlk.dataAcessLayer.entities.cars.Car;
import ru.penlk.dataAcessLayer.entities.cars.CarId;
import ru.penlk.dataAcessLayer.entities.orders.specialOrder.SpecialOrder;
import ru.penlk.dataAcessLayer.entities.orders.specialOrder.SpecialOrderId;
import ru.penlk.dataAcessLayer.entities.orders.specialOrder.SpecialOrderState;
import ru.penlk.dataAcessLayer.entities.users.clients.ClientId;
import ru.penlk.dataAcessLayer.entities.users.managers.Manager;
import ru.penlk.dataAcessLayer.entities.users.managers.ManagerId;
import ru.penlk.dataAcessLayer.entities.valueObjects.Price;
import ru.penlk.dataAcessLayer.repositories.interfaces.carParts.CarPartAlreadyInException;
import ru.penlk.dataAcessLayer.repositories.interfaces.cars.CarRepository;
import ru.penlk.dataAcessLayer.repositories.interfaces.orders.specialOrders.SpecialOrderNotFoundException;
import ru.penlk.dataAcessLayer.repositories.interfaces.orders.specialOrders.SpecialOrderRepository;
import ru.penlk.dataAcessLayer.repositories.interfaces.orders.specialOrders.nodeSets.NodeSetRepository;
import ru.penlk.dataAcessLayer.repositories.interfaces.users.clients.ClientRepository;
import ru.penlk.dataAcessLayer.repositories.interfaces.users.managers.ManagerRepository;

import java.util.Collection;
import java.util.Optional;

@AllArgsConstructor
public class SpecialOrderServiceImpl implements SpecialOrderService {
    private final SpecialOrderRepository specialOrderRepository;
    private final ManagerRepository managerRepository;
    private final ClientRepository clientRepository;
    private final CarRepository carRepository;
    private final NodeSetRepository nodeSetRepository;

    private final CarPartConfigurationService carPartConfigurationService;
    private final RequiredNodeConfigurationService requiredNodeConfigurationService;
    private final CarPartPriceCalculator carPartPriceCalculator;

    private final ManagerSelectionStrategy managerSelectionStrategy;
    private final SpecialStateMapper specialStateMapper;

    @Override
    public SpecialOrderDto create(CreateSpecialOrderDto request) {
        Optional<Car> optionalCar = carRepository.findById(new CarId(request.carId()));

        if (optionalCar.isEmpty()) {
            throw new ServiceException(String.format("Car not found with id {%d}", request.carId()));
        }

        Car car = optionalCar.get();

        Collection<CarPart> carParts = carPartConfigurationService.getCarParts(car, request.specialCarPartIds());

        Collection<CarPart> carPartsAddition = requiredNodeConfigurationService.completeRequireNodes(
                car, carParts
        );

        carPartsAddition.addAll(carParts);

        Price price = carPartPriceCalculator.getSpecialCarPartsPrice(car.getId(), carParts).add(car.getPrice());

        SpecialOrder specialOrder = specialOrderRepository.create(CreateSpecialOrderDto.mapToModel(request, price));

        this.createNodeSet(specialOrder.getId(), carPartsAddition);

        return SpecialOrderDto.mapToDto(specialOrder);
    }

    @Override
    public SpecialOrderDto read(Long id) throws ServiceException {
        Optional<SpecialOrder> carOptional = specialOrderRepository.findById(new SpecialOrderId(id));

        if (carOptional.isPresent()) {
            return SpecialOrderDto.mapToDto(carOptional.get());
        }

        throw new ServiceException(String.format("SpecialOrder with id: {%d} not found", id));
    }

    @Override
    public SpecialOrderDto update(SpecialOrderDto request) throws ServiceException {
        try {
            SpecialOrder mappingSpecialOrder = SpecialOrderDto.mapToModel(request);

            return SpecialOrderDto.mapToDto(
                    specialOrderRepository.update(mappingSpecialOrder)
            );
        } catch (SpecialOrderNotFoundException e) {
            throw new ServiceException(String.format("SpecialOrder with id: {%d} not found", e.getId().id()));
        }
    }

    @Override
    public void delete(Long id) throws ServiceException {
        try {
            specialOrderRepository.delete(new SpecialOrderId(id));
        } catch (SpecialOrderNotFoundException e) {
            throw new ServiceException(String.format("SpecialOrder with id: {%d} not found", id));
        }
    }

    @Override
    public IssueSpecialOrderDto issue(Long clientId, Long carId, Collection<Long> carPartIds) {
        if (clientRepository.findById(new ClientId(clientId)).isEmpty()) {
            throw new ServiceException(String.format("Client with id: {%d} not found", clientId));
        }

        Optional<Car> optionalCar = carRepository.findById(new CarId(carId));

        if (optionalCar.isEmpty()) {
            throw new ServiceException(String.format("Car with id: {%d} not found", carId));
        }

        Car car = optionalCar.get();

        Collection<CarPart> carParts = carPartConfigurationService.getCarParts(car, carPartIds);

        Collection<CarPart> carPartsAddition = requiredNodeConfigurationService.completeRequireNodes(
            car, carParts
        );

        Price totalPrice = carPartPriceCalculator.getSpecialCarPartsPrice(car.getId(), carParts).add(car.getPrice());

        SpecialOrder specialOrder = specialOrderRepository.create(new SpecialOrder(
                        SpecialOrderId.defaultId(),
                        SpecialOrderState.ISSUED,
                        new ClientId(clientId),
                        ManagerId.defaultId(),
                        new CarId(carId),
                        totalPrice
                )
        );

        carPartsAddition.addAll(carParts);

        this.createNodeSet(specialOrder.getId(), carPartsAddition);

        return IssueSpecialOrderDto.mapToDto(specialOrder, totalPrice);
    }

    @Override
    public SpecialOrderDto confirm(Long orderId) throws ServiceException {
        Optional<Manager> optionalManager = managerSelectionStrategy.findManager(managerRepository.findAll());

        if (optionalManager.isEmpty()) {
            throw new ServiceException("Cannot find any manager");
        }

        SpecialOrderFacade orderFacade = getFacade(orderId);

        if (orderFacade.tryConfirm(optionalManager.get().getId()) == Boolean.FALSE) {
            throw new ServiceException(String.format("SpecialOrder with id: {%d} impossible to confirm", orderId));
        }

        return SpecialOrderDto.mapToDto(orderFacade.getOrder());
    }

    @Override
    public SpecialOrderDto waitPurchase(Long orderId) throws ServiceException {
        SpecialOrderFacade orderFacade = getFacade(orderId);

        if (orderFacade.tryWaitPurchase() == Boolean.FALSE) {
            throw new ServiceException(String.format("SpecialOrder with id: {%d} impossible to waiting purchase", orderId));
        }

        return SpecialOrderDto.mapToDto(orderFacade.getOrder());
    }

    @Override
    public SpecialOrderDto purchase(Long orderId) throws ServiceException {
        SpecialOrderFacade orderFacade = getFacade(orderId);

        if (orderFacade.tryPurchase() == Boolean.FALSE) {
            throw new ServiceException(String.format("SpecialOrder with id: {%d} impossible to purchase", orderId));
        }

        return SpecialOrderDto.mapToDto(orderFacade.getOrder());
    }

    @Override
    public SpecialOrderDto carReadyToTake(Long orderId) throws ServiceException {
        SpecialOrderFacade orderFacade = getFacade(orderId);

        if (orderFacade.tryCarReadyToTake() == Boolean.FALSE) {
            throw new ServiceException(String.format("SpecialOrder with id: {%d} impossible to car take", orderId));
        }

        return SpecialOrderDto.mapToDto(orderFacade.getOrder());
    }

    @Override
    public SpecialOrderDto complete(Long orderId) throws ServiceException {
        SpecialOrderFacade orderFacade = getFacade(orderId);

        if (orderFacade.tryComplete() == Boolean.FALSE) {
            throw new ServiceException(String.format("SpecialOrder with id: {%d} impossible to complete", orderId));
        }

        return SpecialOrderDto.mapToDto(orderFacade.getOrder());
    }

    @Override
    public void cancel(Long orderId) throws ServiceException {
        SpecialOrderFacade orderFacade = getFacade(orderId);

        if (orderFacade.tryCancel() == Boolean.FALSE) {
            throw new ServiceException(String.format("SpecialOrder with id: {%d} cancelled", orderId));
        }
    }

    private SpecialOrderFacade getFacade(Long orderId) {
        Optional<SpecialOrder> optionalOrder = specialOrderRepository.findById(new SpecialOrderId(orderId));

        if (optionalOrder.isEmpty()) {
            throw new ServiceException(String.format("SpecialOrder with id: {%d} not found", orderId));
        }

        SpecialOrder order = optionalOrder.get();
        SpecialOrderStateHandler state = specialStateMapper.map(order.getState());

        return new SpecialOrderFacade(new SpecialOrderCore(order, state));
    }

    private void createNodeSet(SpecialOrderId specialOrderId, Collection<CarPart> carParts) {
        try {
            carParts.forEach(x -> nodeSetRepository.create(specialOrderId, x.getId()));
        } catch (CarPartAlreadyInException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
