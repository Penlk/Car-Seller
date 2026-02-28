package ru.penlk.dataBase.repositories.interfaces.orders.commonOrder;

import ru.penlk.dataBase.entities.orders.commonOrder.CommonOrder;
import ru.penlk.dataBase.entities.orders.commonOrder.CommonOrderId;

import java.util.Collection;
import java.util.Optional;

public interface CommonOrderRepository {
    Collection<CommonOrder> findAll();

    Optional<CommonOrder> findById(CommonOrderId id) throws CommonOrderNotFoundException;

    CommonOrder update(CommonOrder commonOrder) throws CommonOrderNotFoundException;

    void delete(CommonOrderId commonOrderId) throws CommonOrderNotFoundException;

    CommonOrder create(CommonOrder commonOrder) throws CommonOrderAlreadyInException;
}
