package ru.penlk.business.contracts.orders;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.penlk.business.contracts.DomainValidationException;
import ru.penlk.business.contracts.ServiceException;
import ru.penlk.dao.entities.orders.common.CommonOrder;

@Service
@Transactional
public interface CommonOrderService {
    CommonOrder create(CommonOrder request);

    CommonOrder read(Long orderId) throws ServiceException;

    CommonOrder update(CommonOrder request) throws ServiceException;

    void delete(Long orderId) throws ServiceException;

    CommonOrder issue(Long clientId, Long carId) throws ServiceException, DomainValidationException;

    CommonOrder confirm(Long orderId) throws ServiceException;

    CommonOrder waitPurchase(Long orderId) throws ServiceException;

    CommonOrder purchase(Long orderId) throws ServiceException;

    CommonOrder carReadyToTake(Long orderId) throws ServiceException;

    CommonOrder complete(Long orderId) throws ServiceException;

    CommonOrder cancel(Long orderId) throws ServiceException;
}
