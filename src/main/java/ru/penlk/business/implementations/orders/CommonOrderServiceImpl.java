package ru.penlk.business.implementations.orders;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
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
import ru.penlk.business.internal.GrantedRoleService;
import ru.penlk.business.internal.RequiredNodeConfigurationService;
import ru.penlk.dao.entities.cars.Car;
import ru.penlk.dao.entities.orders.common.CommonOrder;
import ru.penlk.dao.entities.orders.common.CommonOrderState;
import ru.penlk.dao.entities.outbox.OrderType;
import ru.penlk.dao.entities.outbox.OutboxEvent;
import ru.penlk.dao.repositories.interfaces.cars.CarRepository;
import ru.penlk.dao.repositories.interfaces.orders.common.CommonOrderRepository;
import ru.penlk.dao.repositories.interfaces.outbox.OutboxRepository;
import ru.penlk.presentation.mapping.outbox.OutboxEventMapper;
import ru.penlk.presentation.outbox.models.OutboxEventDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class CommonOrderServiceImpl implements CommonOrderService {
    private final CommonOrderRepository commonOrderRepository;
    private final CarRepository carRepository;
    private final OutboxRepository outboxRepository;
    private final KeycloakAdminService keycloakAdminService;
    private final GrantedRoleService grantedRoleService;

    private final ManagerSelectionStrategy managerSelectionStrategy;
    private final CommonStateMapper commonStateMapper;
    private final OutboxEventMapper outboxEventMapper;
    private final RequiredNodeConfigurationService requiredNodeConfigurationService;

    @Value("${kafka.events.output}")
    private String outputEvent;

    @Value("${kafka.topics.output}")
    private String outputTopic;

    private final KafkaTemplate<Long, OutboxEventDto> kafka;

    public CommonOrderServiceImpl(CommonOrderRepository commonOrderRepository, CarRepository carRepository, OutboxRepository outboxRepository, KeycloakAdminService keycloakAdminService, GrantedRoleService grantedRoleService, ManagerSelectionStrategy managerSelectionStrategy, CommonStateMapper commonStateMapper, OutboxEventMapper outboxEventMapper, RequiredNodeConfigurationService requiredNodeConfigurationService, KafkaTemplate<Long, OutboxEventDto> kafka) {
        this.commonOrderRepository = commonOrderRepository;
        this.carRepository = carRepository;
        this.outboxRepository = outboxRepository;
        this.keycloakAdminService = keycloakAdminService;
        this.grantedRoleService = grantedRoleService;
        this.managerSelectionStrategy = managerSelectionStrategy;
        this.commonStateMapper = commonStateMapper;
        this.outboxEventMapper = outboxEventMapper;
        this.requiredNodeConfigurationService = requiredNodeConfigurationService;
        this.kafka = kafka;
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'USER')")
    public List<CommonOrder> findAll() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null) {
            throw new ServiceException("Authentication required");
        }

        String userId = auth.getName();

        if (grantedRoleService.hasRole("ADMIN")) {
            return commonOrderRepository.findAll();
        }

        if (grantedRoleService.hasRole("MANAGER")) {
            return commonOrderRepository.findAllByManagerId(userId);
        }

        return commonOrderRepository.findAllByOwnerId(userId);
    }

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
        CommonOrderFacade orderFacade = getFacade(getOrder(orderId));

        if (orderFacade.tryConfirm() == Boolean.FALSE) {
            throw new ServiceException(String.format("CommonOrder with id: {%d} impossible to confirm", orderId));
        }

        return commonOrderRepository.save(orderFacade.getOrder());
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public CommonOrder waitPurchase(Long orderId) throws ServiceException {
        CommonOrderFacade orderFacade = getFacade(getOrder(orderId));

        if (orderFacade.tryWaitPurchase() == Boolean.FALSE) {
            throw new ServiceException(String.format("CommonOrder with id: {%d} impossible to waiting purchase", orderId));
        }

        return commonOrderRepository.save(orderFacade.getOrder());
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'USER')")
    public CommonOrder purchase(Long orderId) throws ServiceException {
        CommonOrder order = getOrder(orderId);
        CommonOrderFacade orderFacade = getFacade(order);

        if (orderFacade.tryPurchase() == Boolean.FALSE) {
            throw new ServiceException(String.format("CommonOrder with id: {%d} impossible to purchase", orderId));
        }

        OutboxEvent outboxEvent = new OutboxEvent();
        outboxEvent.setName(outputEvent);
        outboxEvent.setOrderType(OrderType.COMMON);
        outboxEvent.setOrderSourceId(orderId);
        outboxEvent.setCarSourceId(order.getCar().getId());

        outboxRepository.save(outboxEvent);

        return commonOrderRepository.save(orderFacade.getOrder());
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public CommonOrder carReadyToTake(Long orderId) throws ServiceException {
        CommonOrderFacade orderFacade = getFacade(getOrder(orderId));

        if (orderFacade.tryCarReadyToTake() == Boolean.FALSE) {
            throw new ServiceException(String.format("CommonOrder with id: {%d} impossible to car take", orderId));
        }

        return commonOrderRepository.save(orderFacade.getOrder());
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public CommonOrder complete(Long orderId) throws ServiceException {
        CommonOrderFacade orderFacade = getFacade(getOrder(orderId));

        if (orderFacade.tryComplete() == Boolean.FALSE) {
            throw new ServiceException(String.format("CommonOrder with id: {%d} impossible to complete", orderId));
        }

        return commonOrderRepository.save(orderFacade.getOrder());
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'USER')")
    public CommonOrder cancel(Long orderId) throws ServiceException {
        CommonOrderFacade orderFacade = getFacade(getOrder(orderId));

        if (orderFacade.tryCancel() == Boolean.FALSE) {
            throw new ServiceException(String.format("CommonOrder with id: {%d} impossible to cancel", orderId));
        }

        return commonOrderRepository.save(orderFacade.getOrder());
    }

    private CommonOrder getOrder(Long orderId) throws ServiceException {
        Optional<CommonOrder> optionalOrder = commonOrderRepository.findById(orderId);

        if (optionalOrder.isEmpty()) {
            throw new ServiceException(String.format("CommonOrder with id: {%d} not found", orderId));
        }

        return optionalOrder.get();
    }

    private CommonOrderFacade getFacade(CommonOrder order) throws ServiceException {
        CommonOrderStateHandler state = commonStateMapper.map(order.getState());

        return new CommonOrderFacade(new CommonOrderCore(order, state));
    }

    @Scheduled(cron = "${outbox.cron}")
    public void publishOutbox() {
        Set<OutboxEvent> outboxEvents = outboxRepository.findTop100ByOrderByCreatedAtAsc();

        for (OutboxEvent outboxEvent : outboxEvents) {
            OutboxEventDto dto = outboxEventMapper.map(outboxEvent);

            try {
                kafka.send(
                        outputTopic,
                        dto.orderSourceId(),
                        dto
                ).get();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return;
            }

            outboxRepository.delete(outboxEvent);
        }
    }
}
