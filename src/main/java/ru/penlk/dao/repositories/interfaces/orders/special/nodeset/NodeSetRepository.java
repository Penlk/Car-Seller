package ru.penlk.dao.repositories.interfaces.orders.special.nodeset;

import ru.penlk.dao.entities.orders.special.SpecialOrderId;
import ru.penlk.dao.repositories.interfaces.cars.parts.CarPartAlreadyInException;
import ru.penlk.dao.repositories.interfaces.cars.parts.CarPartNotFoundException;
import ru.penlk.dao.repositories.interfaces.orders.special.SpecialOrderNotFoundException;

import java.util.Collection;

public interface NodeSetRepository {
    Collection<CarPartId> findByOrderId(SpecialOrderId specialOrderId) throws SpecialOrderNotFoundException;

    void create(SpecialOrderId specialOrderId, CarPartId carPartId) throws CarPartAlreadyInException;

    void deleteById(SpecialOrderId specialOrderId) throws SpecialOrderNotFoundException;

    void delete(SpecialOrderId specialOrderId, CarPartId carPartId) throws
            SpecialOrderNotFoundException, CarPartNotFoundException;
}
