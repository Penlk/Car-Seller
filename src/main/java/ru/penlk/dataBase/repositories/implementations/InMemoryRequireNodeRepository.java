package ru.penlk.dataBase.repositories.implementations;

import ru.penlk.dataBase.entities.cars.CarId;
import ru.penlk.dataBase.entities.nodes.NodeId;
import ru.penlk.dataBase.repositories.interfaces.carParts.CarPartAlreadyInException;
import ru.penlk.dataBase.repositories.interfaces.carParts.CarPartNotFoundException;
import ru.penlk.dataBase.repositories.interfaces.cars.CarNotFoundException;
import ru.penlk.dataBase.repositories.interfaces.nodes.NodeAlreadyInException;
import ru.penlk.dataBase.repositories.interfaces.nodes.NodeNotFoundException;
import ru.penlk.dataBase.repositories.interfaces.requireNodes.RequireNodeRepository;

import java.util.*;

public class InMemoryRequireNodeRepository implements RequireNodeRepository {
    Map<CarId, List<NodeId>> nodes = new HashMap<>();

    @Override
    public Collection<NodeId> findByCarId(CarId carId) throws CarNotFoundException {
        if (!nodes.containsKey(carId)) {
            throw new CarNotFoundException(carId);
        }

        return nodes.get(carId);
    }

    @Override
    public void delete(CarId carId, NodeId nodeId) throws CarNotFoundException, NodeNotFoundException {
        if (!nodes.containsKey(carId)) {
            throw new CarNotFoundException(carId);
        }

        List<NodeId> nodes = this.nodes.get(carId);

        if (!nodes.removeIf(nodeId::equals)) {
            throw new NodeNotFoundException(nodeId);
        }
    }

    @Override
    public void create(CarId carId, NodeId nodeId) throws NodeAlreadyInException {
        if (!nodes.containsKey(carId)) {
            nodes.put(carId, new ArrayList<>());
        }

        List<NodeId> nodes = this.nodes.get(carId);

        if (nodes.contains(nodeId)) {
            throw new NodeAlreadyInException(nodeId);
        }

        nodes.add(nodeId);
    }
}
