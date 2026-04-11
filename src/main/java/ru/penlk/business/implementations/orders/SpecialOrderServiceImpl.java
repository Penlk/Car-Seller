package ru.penlk.business.implementations.orders;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.penlk.business.contracts.ServiceException;
import ru.penlk.business.contracts.orders.SpecialOrderService;
import ru.penlk.business.implementations.orders.states.mappers.SpecialStateMapper;
import ru.penlk.business.implementations.orders.states.special.SpecialOrderCore;
import ru.penlk.business.implementations.orders.states.special.SpecialOrderFacade;
import ru.penlk.business.implementations.orders.states.special.SpecialOrderStateHandler;
import ru.penlk.business.implementations.orders.strategies.ManagerSelectionStrategy;
import ru.penlk.business.internal.CarPartPriceCalculator;
import ru.penlk.business.internal.RequiredNodeConfigurationService;
import ru.penlk.dao.entities.cars.Car;
import ru.penlk.dao.entities.cars.CarPart;
import ru.penlk.dao.entities.configurations.specials.Configurator;
import ru.penlk.dao.entities.configurations.specials.ConfiguratorCarPart;
import ru.penlk.dao.entities.configurations.specials.SpecialConfiguration;
import ru.penlk.dao.entities.orders.special.SpecialAllowedPart;
import ru.penlk.dao.entities.orders.special.SpecialOrder;
import ru.penlk.dao.entities.orders.special.SpecialOrderState;
import ru.penlk.dao.entities.users.clients.Client;
import ru.penlk.dao.entities.users.managers.Manager;
import ru.penlk.dao.entities.vo.Price;
import ru.penlk.dao.repositories.interfaces.configurations.ConfiguratorRepository;
import ru.penlk.dao.repositories.interfaces.orders.special.SpecialOrderRepository;
import ru.penlk.dao.repositories.interfaces.users.clients.ClientRepository;
import ru.penlk.dao.repositories.interfaces.users.managers.ManagerRepository;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Service
@Transactional
public class SpecialOrderServiceImpl implements SpecialOrderService {
    private final SpecialOrderRepository specialOrderRepository;
    private final ManagerRepository managerRepository;
    private final ClientRepository clientRepository;
    private final ConfiguratorRepository configuratorRepository;

    private final RequiredNodeConfigurationService requiredNodeConfigurationService;
    private final CarPartPriceCalculator carPartPriceCalculator;

    private final ManagerSelectionStrategy managerSelectionStrategy;
    private final SpecialStateMapper specialStateMapper;

    @Override
    public SpecialOrder find(Long orderId) throws ServiceException {
        Optional<SpecialOrder> specialOrderOptional = specialOrderRepository.findById(orderId);

        if (specialOrderOptional.isPresent()) {
            return specialOrderOptional.get();
        }

        throw new ServiceException(String.format("SpecialOrder with id: {%d} not found", orderId));
    }

    @Override
    public void delete(Long id) throws ServiceException {
        specialOrderRepository.deleteById(id);
    }

    @Override
    public SpecialOrder placement(Long clientId, Long configuratorId) {
        Optional<Client> clientOptional = clientRepository.findById(clientId);

        if (clientOptional.isEmpty()) {
            throw new ServiceException(String.format("Client with id: {%d} not found", clientId));
        }

        Optional<Configurator> optionalConfigurator = configuratorRepository.findById(configuratorId);

        if (optionalConfigurator.isEmpty()) {
            throw new ServiceException(String.format("Configurator with id: {%d} not found", configuratorId));
        }

        if (optionalConfigurator.get().isFinished()) {
            throw new ServiceException(String.format("Configurator with id: {%d} is finished", configuratorId));
        }

        Car car = optionalConfigurator.get().getCar();

        Collection<CarPart> carParts = optionalConfigurator.get().getSpecialConfigurations().stream()
                .map(x -> x.getSpecialAllowedPart().getCarPart())
                .toList();

        Set<ConfiguratorCarPart> carPartsAddition = new HashSet<>(
                requiredNodeConfigurationService.completeRequireNodes(
                                car, carParts
                        ).stream()
                        .map(x -> new ConfiguratorCarPart(optionalConfigurator.get(), x))
                        .toList()
        );

        optionalConfigurator.get().setCarParts(carPartsAddition);
        optionalConfigurator.get().setFinished(true);

        List<SpecialAllowedPart> specialCarParts = optionalConfigurator.get().getSpecialConfigurations().stream()
                .map(SpecialConfiguration::getSpecialAllowedPart)
                .toList();

        Price totalPrice = carPartPriceCalculator.getSpecialCarPartsPrice(specialCarParts).add(car.getPrice());

        return specialOrderRepository.save(new SpecialOrder(
                        SpecialOrderState.PLACED,
                        clientOptional.get(),
                        null,
                        car,
                        optionalConfigurator.get(),
                        totalPrice
                )
        );
    }

    @Override
    public SpecialOrder confirm(Long orderId) throws ServiceException {
        Optional<Manager> optionalManager = managerSelectionStrategy.findManager(managerRepository.findAll());

        if (optionalManager.isEmpty()) {
            throw new ServiceException("Cannot find any manager");
        }

        SpecialOrderFacade orderFacade = getFacade(orderId);

        if (orderFacade.tryConfirm(optionalManager.get()) == Boolean.FALSE) {
            throw new ServiceException(String.format("SpecialOrder with id: {%d} impossible to confirm", orderId));
        }

        return specialOrderRepository.save(orderFacade.getOrder());
    }

    @Override
    public SpecialOrder waitPurchase(Long orderId) throws ServiceException {
        SpecialOrderFacade orderFacade = getFacade(orderId);

        if (orderFacade.tryWaitPurchase() == Boolean.FALSE) {
            throw new ServiceException(String.format("SpecialOrder with id: {%d} impossible to waiting purchase", orderId));
        }

        return specialOrderRepository.save(orderFacade.getOrder());
    }

    @Override
    public SpecialOrder purchase(Long orderId) throws ServiceException {
        SpecialOrderFacade orderFacade = getFacade(orderId);

        if (orderFacade.tryPurchase() == Boolean.FALSE) {
            throw new ServiceException(String.format("SpecialOrder with id: {%d} impossible to purchase", orderId));
        }

        return specialOrderRepository.save(orderFacade.getOrder());
    }

    @Override
    public SpecialOrder carReadyToTake(Long orderId) throws ServiceException {
        SpecialOrderFacade orderFacade = getFacade(orderId);

        if (orderFacade.tryCarReadyToTake() == Boolean.FALSE) {
            throw new ServiceException(String.format("SpecialOrder with id: {%d} impossible to car take", orderId));
        }

        return specialOrderRepository.save(orderFacade.getOrder());
    }

    @Override
    public SpecialOrder complete(Long orderId) throws ServiceException {
        SpecialOrderFacade orderFacade = getFacade(orderId);

        if (orderFacade.tryComplete() == Boolean.FALSE) {
            throw new ServiceException(String.format("SpecialOrder with id: {%d} impossible to complete", orderId));
        }

        return specialOrderRepository.save(orderFacade.getOrder());
    }

    @Override
    public SpecialOrder cancel(Long orderId) throws ServiceException {
        SpecialOrderFacade orderFacade = getFacade(orderId);

        if (orderFacade.tryCancel() == Boolean.FALSE) {
            throw new ServiceException(String.format("SpecialOrder with id: {%d} cancelled", orderId));
        }

        return specialOrderRepository.save(orderFacade.getOrder());
    }

    private SpecialOrderFacade getFacade(Long orderId) {
        Optional<SpecialOrder> optionalOrder = specialOrderRepository.findById(orderId);

        if (optionalOrder.isEmpty()) {
            throw new ServiceException(String.format("SpecialOrder with id: {%d} not found", orderId));
        }

        SpecialOrder order = optionalOrder.get();
        SpecialOrderStateHandler state = specialStateMapper.map(order.getState());

        return new SpecialOrderFacade(new SpecialOrderCore(order, state));
    }
}
