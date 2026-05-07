package ru.penlk.business.contracts.orders;

import ru.penlk.business.contracts.DomainValidationException;
import ru.penlk.business.contracts.ServiceException;
import ru.penlk.dao.entities.orders.common.CommonOrder;

import java.util.List;

public interface CommonOrderService {
    List<CommonOrder> findAll();

    CommonOrder find(Long orderId) throws ServiceException;

    void delete(Long orderId) throws ServiceException;

    CommonOrder placement(Long carId) throws ServiceException, DomainValidationException;

    CommonOrder confirm(Long orderId) throws ServiceException;

    CommonOrder waitPurchase(Long orderId) throws ServiceException;

    CommonOrder purchase(Long orderId) throws ServiceException;

    CommonOrder carReadyToTake(Long orderId) throws ServiceException;

    CommonOrder complete(Long orderId) throws ServiceException;

    CommonOrder cancel(Long orderId) throws ServiceException;
}
