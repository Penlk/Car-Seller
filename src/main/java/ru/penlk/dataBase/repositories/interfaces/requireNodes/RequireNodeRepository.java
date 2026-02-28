package ru.penlk.dataBase.repositories.interfaces.requireNodes;

import ru.penlk.dataBase.entities.cars.CarId;
import ru.penlk.dataBase.entities.carParts.CarPartId;
import ru.penlk.dataBase.entities.nodes.NodeId;
import ru.penlk.dataBase.repositories.interfaces.cars.CarAlreadyInException;
import ru.penlk.dataBase.repositories.interfaces.cars.CarNotFoundException;
import ru.penlk.dataBase.repositories.interfaces.carParts.CarPartAlreadyInException;
import ru.penlk.dataBase.repositories.interfaces.carParts.CarPartNotFoundException;
import ru.penlk.dataBase.repositories.interfaces.nodes.NodeNotFoundException;

import java.util.Collection;

public interface RequireNodeRepository {
    Collection<NodeId> findByCarId(CarId carId) throws CarNotFoundException;

    void delete(CarId carId, NodeId nodeId) throws CarNotFoundException, NodeNotFoundException;

    // NodeId updateNode(CarId carId, NodeId nodeId) throws CarNotFoundException, NodeNotFoundException;

    void create(CarId carId, NodeId nodeId) throws NodeNotFoundException;
}
