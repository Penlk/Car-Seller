package ru.penlk.dao.repositories.interfaces.nodes.require;

import ru.penlk.dao.entities.cars.CarId;
import ru.penlk.dao.entities.nodes.NodeId;
import ru.penlk.dao.repositories.interfaces.cars.CarNotFoundException;
import ru.penlk.dao.repositories.interfaces.nodes.NodeNotFoundException;

import java.util.Collection;

public interface RequireNodeRepository {
    Collection<NodeId> findByCarId(CarId carId) throws CarNotFoundException;

    void delete(CarId carId, NodeId nodeId) throws CarNotFoundException, NodeNotFoundException;

    // NodeId updateNode(CarId carId, NodeId nodeId) throws CarNotFoundException, NodeNotFoundException;

    void create(CarId carId, NodeId nodeId) throws NodeNotFoundException;
}
