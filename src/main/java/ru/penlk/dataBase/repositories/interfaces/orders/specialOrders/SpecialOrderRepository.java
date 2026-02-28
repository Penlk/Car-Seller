package ru.penlk.dataBase.repositories.interfaces.orders.specialOrders;

import ru.penlk.dataBase.entities.orders.specialOrder.SpecialOrder;
import ru.penlk.dataBase.entities.orders.specialOrder.SpecialOrderId;

import java.util.Collection;
import java.util.Optional;

public interface SpecialOrderRepository {
    Optional<SpecialOrder> findById(SpecialOrderId specialOrderId);

    Collection<SpecialOrder> findAll();

    void delete(SpecialOrderId specialOrderId) throws SpecialOrderNotFoundException;

    SpecialOrder update(SpecialOrder specialOrder) throws SpecialOrderNotFoundException;

    SpecialOrder create(SpecialOrder order) throws SpecialOrderAlreadyInException;
}
