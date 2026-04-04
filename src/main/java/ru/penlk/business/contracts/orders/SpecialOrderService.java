package ru.penlk.business.contracts.orders;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.penlk.business.contracts.DomainValidationException;
import ru.penlk.business.contracts.IncompatibleComponentException;
import ru.penlk.business.contracts.ServiceException;
import ru.penlk.dao.entities.orders.special.SpecialOrder;

@Service
@Transactional
public interface SpecialOrderService {
    SpecialOrder read(Long orderId) throws ServiceException;

    void delete(Long orderId) throws ServiceException;

    SpecialOrder issue(Long clientId, Long configuratorId) throws
            ServiceException,
            DomainValidationException,
            IncompatibleComponentException;

    SpecialOrder confirm(Long orderId) throws ServiceException;

    SpecialOrder waitPurchase(Long orderId) throws ServiceException;

    SpecialOrder purchase(Long orderId) throws ServiceException;

    SpecialOrder carReadyToTake(Long orderId) throws ServiceException;

    SpecialOrder complete(Long orderId) throws ServiceException;

    void cancel(Long orderId) throws ServiceException;
}
