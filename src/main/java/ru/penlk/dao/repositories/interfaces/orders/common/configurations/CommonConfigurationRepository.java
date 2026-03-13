package ru.penlk.dao.repositories.interfaces.orders.common.configurations;

import ru.penlk.dao.entities.carParts.CarPartId;
import ru.penlk.dao.entities.cars.CarId;
import ru.penlk.dao.repositories.interfaces.cars.parts.CarPartAlreadyInException;
import ru.penlk.dao.repositories.interfaces.cars.parts.CarPartNotFoundException;
import ru.penlk.dao.repositories.interfaces.cars.CarNotFoundException;

import java.util.Collection;

public interface CommonConfigurationRepository {
    Collection<CarPartId> findByCarId(CarId carId) throws CarNotFoundException;

    void delete(CarId carId, CarPartId carPartId) throws CarNotFoundException, CarPartNotFoundException;

    // CarPartId updateNode(CarId carId, CarPartId carPartId) throws CarNotFoundException, CarPartNotFoundException;
    void create(CarId carId, CarPartId carPartId) throws CarPartAlreadyInException;
}
