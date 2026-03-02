package ru.penlk.businessLayer.implementations.orders;

import lombok.AllArgsConstructor;
import ru.penlk.businessLayer.contracts.ServiceException;
import ru.penlk.businessLayer.contracts.commonOrders.CommonOrderService;
import ru.penlk.businessLayer.contracts.commonOrders.models.CommonOrderDto;
import ru.penlk.businessLayer.contracts.commonOrders.models.CreateCommonOrderDto;
import ru.penlk.dataAcessLayer.entities.cars.CarId;
import ru.penlk.dataAcessLayer.entities.orders.commonOrder.CommonOrder;
import ru.penlk.dataAcessLayer.entities.orders.commonOrder.CommonOrderId;
import ru.penlk.dataAcessLayer.entities.orders.commonOrder.CommonOrderState;
import ru.penlk.dataAcessLayer.entities.users.clients.ClientId;
import ru.penlk.dataAcessLayer.entities.users.managers.Manager;
import ru.penlk.dataAcessLayer.repositories.interfaces.orders.commonOrder.CommonOrderNotFoundException;
import ru.penlk.dataAcessLayer.repositories.interfaces.orders.commonOrder.CommonOrderRepository;
import ru.penlk.dataAcessLayer.repositories.interfaces.users.clients.ClientRepository;
import ru.penlk.dataAcessLayer.repositories.interfaces.users.managers.ManagerRepository;

import java.util.Optional;

@AllArgsConstructor
public class DefaultCommonOrderService implements CommonOrderService {
    private final CommonOrderRepository commonOrderRepository;
    private final ManagerRepository managerRepository;
    private final ClientRepository clientRepository;
    private final ManagerSelectionStrategy managerSelectionStrategy;

    @Override
    public CommonOrderDto create(CreateCommonOrderDto request) {
        CommonOrder car = commonOrderRepository.create(CreateCommonOrderDto.MapToModel(request));

        return CommonOrderDto.MapToDto(car);
    }

    @Override
    public CommonOrderDto read(Long id) throws ServiceException {
        Optional<CommonOrder> carOptional = commonOrderRepository.findById(new CommonOrderId(id));

        if (carOptional.isPresent()) {
            return CommonOrderDto.MapToDto(carOptional.get());
        }

        throw new ServiceException(String.format("CommonOrder with id: {%d} not found", id));
    }

    @Override
    public CommonOrderDto update(CommonOrderDto request) throws ServiceException {
        try {
            CommonOrder mappingCommonOrder = CommonOrderDto.MapToModel(request);

            return CommonOrderDto.MapToDto(
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
    public IssueCommonOrderDto issue(Long clientId, Long carId) throws ServiceException {
        if (clientRepository.findById(new ClientId(clientId)).isEmpty()) {
            throw new ServiceException(String.format("Client with id: {%d} not found", clientId));
        }

        Optional<Manager> optionalManager = managerSelectionStrategy.findManager(managerRepository);

        if (optionalManager.isEmpty()) {
            throw new ServiceException("Cannot find any manager");
        }

        commonOrderRepository.create(new CommonOrder(
                CommonOrderId.defaultId(),
                CommonOrderState.Issued,
                new ClientId(clientId),
                optionalManager.get().getId(),
                new CarId(carId)
        ));
    }

    @Override
    public CommonOrderDto confirm(Long orderId) throws ServiceException {
        return null;
    }

    @Override
    public CommonOrderDto waitPurchase(Long orderId) throws ServiceException {
        return null;
    }

    @Override
    public CommonOrderDto purchase(Long orderId) throws ServiceException {
        return null;
    }

    @Override
    public CommonOrderDto carReadyToTake(Long orderId) throws ServiceException {
        return null;
    }

    @Override
    public CommonOrderDto completed(Long orderId) throws ServiceException {
        return null;
    }

    @Override
    public void cancel(Long orderId) throws ServiceException {

    }
}
