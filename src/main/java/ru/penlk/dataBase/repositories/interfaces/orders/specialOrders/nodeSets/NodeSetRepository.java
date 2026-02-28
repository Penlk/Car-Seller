package ru.penlk.dataBase.repositories.interfaces.orders.specialOrders.nodeSets;

import ru.penlk.dataBase.entities.carParts.CarPartId;
import ru.penlk.dataBase.entities.orders.specialOrder.SpecialOrderId;
import ru.penlk.dataBase.repositories.interfaces.carParts.CarPartAlreadyInException;
import ru.penlk.dataBase.repositories.interfaces.carParts.CarPartNotFoundException;
import ru.penlk.dataBase.repositories.interfaces.orders.specialOrders.SpecialOrderAlreadyInException;
import ru.penlk.dataBase.repositories.interfaces.orders.specialOrders.SpecialOrderNotFoundException;

import java.util.Collection;

public interface NodeSetRepository {
    Collection<CarPartId> findByOrderId(SpecialOrderId specialOrderId);
    void create(SpecialOrderId specialOrderId, CarPartId carPartId) throws CarPartAlreadyInException;
    void deleteById(SpecialOrderId specialOrderId) throws SpecialOrderNotFoundException;
    void delete(SpecialOrderId specialOrderId, CarPartId carPartId) throws
            SpecialOrderNotFoundException, CarPartNotFoundException;
}
