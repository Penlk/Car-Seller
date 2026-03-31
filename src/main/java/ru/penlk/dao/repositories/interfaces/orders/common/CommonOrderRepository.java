package ru.penlk.dao.repositories.interfaces.orders.common;

import ru.penlk.dao.entities.orders.common.CommonOrder;
import ru.penlk.dao.entities.orders.common.CommonOrderId;

import java.util.Collection;
import java.util.Optional;

public interface CommonOrderRepository {
    Collection<CommonOrder> findAll();

    Optional<CommonOrder> findById(CommonOrderId id) throws CommonOrderNotFoundException;

    CommonOrder update(CommonOrder commonOrder) throws CommonOrderNotFoundException;

    void delete(CommonOrderId commonOrderId) throws CommonOrderNotFoundException;

    CommonOrder create(CommonOrder commonOrder);
}
