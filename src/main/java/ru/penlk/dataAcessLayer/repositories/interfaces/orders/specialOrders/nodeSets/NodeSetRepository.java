package ru.penlk.dataAcessLayer.repositories.interfaces.orders.specialOrders.nodeSets;

import ru.penlk.dataAcessLayer.entities.carParts.CarPartId;
import ru.penlk.dataAcessLayer.entities.orders.specialOrder.SpecialOrderId;
import ru.penlk.dataAcessLayer.repositories.interfaces.carParts.CarPartAlreadyInException;
import ru.penlk.dataAcessLayer.repositories.interfaces.carParts.CarPartNotFoundException;
import ru.penlk.dataAcessLayer.repositories.interfaces.orders.specialOrders.SpecialOrderNotFoundException;

import java.util.Collection;

public interface NodeSetRepository {
    Collection<CarPartId> findByOrderId(SpecialOrderId specialOrderId) throws SpecialOrderNotFoundException;

    void create(SpecialOrderId specialOrderId, CarPartId carPartId) throws CarPartAlreadyInException;

    void deleteById(SpecialOrderId specialOrderId) throws SpecialOrderNotFoundException;

    void delete(SpecialOrderId specialOrderId, CarPartId carPartId) throws
            SpecialOrderNotFoundException, CarPartNotFoundException;
}
