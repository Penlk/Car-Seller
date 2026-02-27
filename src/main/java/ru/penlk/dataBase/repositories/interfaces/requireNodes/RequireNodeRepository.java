package ru.penlk.dataBase.repositories.interfaces.requireNodes;

import ru.penlk.dataBase.entities.cars.CarId;
import ru.penlk.dataBase.entities.carParts.CarPartId;
import ru.penlk.dataBase.repositories.interfaces.cars.CarAlreadyInException;
import ru.penlk.dataBase.repositories.interfaces.cars.CarNotFoundException;
import ru.penlk.dataBase.repositories.interfaces.carParts.CarPartAlreadyInException;
import ru.penlk.dataBase.repositories.interfaces.carParts.CarPartNotFoundException;

import java.util.Collection;

public interface RequireNodeRepository {
    Collection<String> findByCarId(CarId carId) throws CarNotFoundException;
    void delete(CarId carId, CarPartId nodeId) throws CarNotFoundException, CarPartNotFoundException;
    // NodeId updateNode(CarId carId, NodeId nodeId) throws CarNotFoundException, NodeNotFoundException;
    void create(CarId carId, CarPartId nodeId) throws CarAlreadyInException, CarPartAlreadyInException;
}
