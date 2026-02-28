package ru.penlk.dataBase.repositories.implementations;

import ru.penlk.dataBase.entities.carParts.CarPartId;
import ru.penlk.dataBase.entities.orders.specialOrder.SpecialOrderId;
import ru.penlk.dataBase.repositories.interfaces.carParts.CarPartAlreadyInException;
import ru.penlk.dataBase.repositories.interfaces.carParts.CarPartNotFoundException;
import ru.penlk.dataBase.repositories.interfaces.orders.specialOrders.SpecialOrderNotFoundException;
import ru.penlk.dataBase.repositories.interfaces.orders.specialOrders.nodeSets.NodeSetRepository;

import java.util.*;

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
