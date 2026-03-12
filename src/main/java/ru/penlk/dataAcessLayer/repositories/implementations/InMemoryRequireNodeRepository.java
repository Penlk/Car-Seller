package ru.penlk.dataAcessLayer.repositories.implementations;

import ru.penlk.dataAcessLayer.entities.cars.CarId;
import ru.penlk.dataAcessLayer.entities.nodes.NodeId;
import ru.penlk.dataAcessLayer.repositories.interfaces.cars.CarNotFoundException;
import ru.penlk.dataAcessLayer.repositories.interfaces.nodes.NodeAlreadyInException;
import ru.penlk.dataAcessLayer.repositories.interfaces.nodes.NodeNotFoundException;
import ru.penlk.dataAcessLayer.repositories.interfaces.requireNodes.RequireNodeRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
