package ru.penlk.businessLayer.contracts.specialOrders;

import ru.penlk.businessLayer.contracts.DomainValidationException;
import ru.penlk.businessLayer.contracts.IncompatibleComponentException;
import ru.penlk.businessLayer.contracts.ServiceException;
import ru.penlk.businessLayer.contracts.specialOrders.models.CreateSpecialOrderDto;
import ru.penlk.businessLayer.contracts.specialOrders.models.IssueSpecialOrderDto;
import ru.penlk.businessLayer.contracts.specialOrders.models.SpecialOrderDto;

import java.util.Collection;

public interface SpecialOrderService {
    SpecialOrderDto create(CreateSpecialOrderDto request);

    SpecialOrderDto read(Long id) throws ServiceException;

    SpecialOrderDto update(SpecialOrderDto request) throws ServiceException;

    void delete(Long id) throws ServiceException;

    IssueSpecialOrderDto issue(Long clientId, Long carId, Collection<Long> carPartIds) throws
            ServiceException,
            DomainValidationException,
            IncompatibleComponentException;

    SpecialOrderDto confirm(Long orderId) throws ServiceException;

    SpecialOrderDto waitPurchase(Long orderId) throws ServiceException;

    SpecialOrderDto purchase(Long orderId) throws ServiceException;

    SpecialOrderDto carReadyToTake(Long orderId) throws ServiceException;

    SpecialOrderDto complete(Long orderId) throws ServiceException;

    void cancel(Long orderId) throws ServiceException;
}
