package ru.penlk.business.contracts.orders;

import ru.penlk.business.contracts.DomainValidationException;
import ru.penlk.business.contracts.IncompatibleComponentException;
import ru.penlk.business.contracts.ServiceException;
import ru.penlk.dao.entities.orders.special.SpecialOrder;

import java.util.List;

public interface SpecialOrderService {
    SpecialOrder find(Long orderId) throws ServiceException;

    List<SpecialOrder> findAll() throws ServiceException;

    void delete(Long orderId) throws ServiceException;

    SpecialOrder placement(Long configuratorId) throws
            ServiceException,
            DomainValidationException,
            IncompatibleComponentException;

    SpecialOrder confirm(Long orderId) throws ServiceException;

    SpecialOrder waitPurchase(Long orderId) throws ServiceException;

    SpecialOrder purchase(Long orderId) throws ServiceException;

    SpecialOrder carReadyToTake(Long orderId) throws ServiceException;

    SpecialOrder complete(Long orderId) throws ServiceException;

    SpecialOrder cancel(Long orderId) throws ServiceException;
}
