package ru.penlk.business.contracts.orders.common;

import ru.penlk.business.contracts.DomainValidationException;
import ru.penlk.business.contracts.ServiceException;
import ru.penlk.business.contracts.orders.common.models.CommonOrderDto;
import ru.penlk.business.contracts.orders.common.models.CreateCommonOrderDto;
import ru.penlk.business.contracts.orders.common.models.IssueCommonOrderDto;

public interface CommonOrderService {
    CommonOrderDto create(CreateCommonOrderDto request);

    CommonOrderDto read(Long id) throws ServiceException;

    CommonOrderDto update(CommonOrderDto request) throws ServiceException;

    void delete(Long id) throws ServiceException;

    IssueCommonOrderDto issue(Long clientId, Long carId) throws ServiceException, DomainValidationException;

    CommonOrderDto confirm(Long orderId) throws ServiceException;

    CommonOrderDto waitPurchase(Long orderId) throws ServiceException;

    CommonOrderDto purchase(Long orderId) throws ServiceException;

    CommonOrderDto carReadyToTake(Long orderId) throws ServiceException;

    CommonOrderDto complete(Long orderId) throws ServiceException;

    void cancel(Long orderId) throws ServiceException;
}
