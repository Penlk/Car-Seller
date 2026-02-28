package ru.penlk.dataAcessLayer.repositories.interfaces.requireNodes;

import ru.penlk.dataAcessLayer.entities.cars.CarId;
import ru.penlk.dataAcessLayer.entities.nodes.NodeId;
import ru.penlk.dataAcessLayer.repositories.interfaces.cars.CarNotFoundException;
import ru.penlk.dataAcessLayer.repositories.interfaces.nodes.NodeNotFoundException;

import java.util.Collection;

public interface RequireNodeRepository {
    Collection<NodeId> findByCarId(CarId carId) throws CarNotFoundException;

    void delete(CarId carId, NodeId nodeId) throws CarNotFoundException, NodeNotFoundException;

    // NodeId updateNode(CarId carId, NodeId nodeId) throws CarNotFoundException, NodeNotFoundException;

    void create(CarId carId, NodeId nodeId) throws NodeNotFoundException;
}
