package ru.penlk.businessLayer.contracts.commonOrders;

import ru.penlk.businessLayer.contracts.ServiceException;
import ru.penlk.businessLayer.contracts.commonOrders.models.CommonOrderDto;
import ru.penlk.businessLayer.contracts.commonOrders.models.CreateCommonOrderDto;

public interface CommonOrderService {
    CommonOrderDto create(CreateCommonOrderDto request);

    CommonOrderDto read(Long id) throws ServiceException;

    CommonOrderDto update(CommonOrderDto request) throws ServiceException;

    void delete(Long id) throws ServiceException;

    IssueCommonOrderDto issue(Long clientId, Long carId) throws ServiceException;

    CommonOrderDto confirm(Long orderId) throws ServiceException;

    CommonOrderDto waitPurchase(Long orderId) throws ServiceException;

    CommonOrderDto purchase(Long orderId) throws ServiceException;

    CommonOrderDto carReadyToTake(Long orderId) throws ServiceException;

    CommonOrderDto completed(Long orderId) throws ServiceException;

    void cancel(Long orderId) throws ServiceException;
}
