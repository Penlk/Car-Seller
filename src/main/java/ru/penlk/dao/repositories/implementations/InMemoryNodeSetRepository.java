package ru.penlk.dao.repositories.implementations;

import ru.penlk.dao.entities.carParts.CarPartId;
import ru.penlk.dao.entities.orders.specialOrder.SpecialOrderId;
import ru.penlk.dao.repositories.interfaces.cars.parts.CarPartAlreadyInException;
import ru.penlk.dao.repositories.interfaces.cars.parts.CarPartNotFoundException;
import ru.penlk.dao.repositories.interfaces.orders.special.SpecialOrderNotFoundException;
import ru.penlk.dao.repositories.interfaces.orders.special.nodeset.NodeSetRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryNodeSetRepository implements NodeSetRepository {
    Map<SpecialOrderId, List<CarPartId>> nodes = new HashMap<>();

    @Override
    public Collection<CarPartId> findByOrderId(SpecialOrderId specialOrderId) throws SpecialOrderNotFoundException {
        if (!nodes.containsKey(specialOrderId)) {
            throw new SpecialOrderNotFoundException(specialOrderId);
        }

        return nodes.get(specialOrderId);
    }

    @Override
    public void delete(SpecialOrderId specialOrderId, CarPartId carPartId) throws SpecialOrderNotFoundException, CarPartNotFoundException {
        if (!nodes.containsKey(specialOrderId)) {
            throw new SpecialOrderNotFoundException(specialOrderId);
        }

        List<CarPartId> carParts = nodes.get(specialOrderId);

        if (!carParts.removeIf(carPartId::equals)) {
            throw new CarPartNotFoundException(carPartId);
        }
    }

    @Override
    public void create(SpecialOrderId specialOrderId, CarPartId carPartId) throws CarPartAlreadyInException {
        if (!nodes.containsKey(specialOrderId)) {
            nodes.put(specialOrderId, new ArrayList<>());
        }

        List<CarPartId> carParts = nodes.get(specialOrderId);

        if (carParts.contains(carPartId)) {
            throw new CarPartAlreadyInException(carPartId);
        }

        carParts.add(carPartId);
    }

    @Override
    public void deleteById(SpecialOrderId specialOrderId) throws SpecialOrderNotFoundException {
        if (nodes.remove(specialOrderId) == null) {
            throw new SpecialOrderNotFoundException(specialOrderId);
        }
    }
}
