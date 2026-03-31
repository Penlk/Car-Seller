package ru.penlk.dao.repositories.interfaces.orders.common.configurations;

import ru.penlk.dao.repositories.interfaces.cars.CarNotFoundException;
import ru.penlk.dao.repositories.interfaces.cars.parts.CarPartAlreadyInException;
import ru.penlk.dao.repositories.interfaces.cars.parts.CarPartNotFoundException;

import java.util.Collection;

public interface CommonConfigurationRepository {
    Collection<CarPartId> findByCarId(CarId carId) throws CarNotFoundException;

    void delete(CarId carId, CarPartId carPartId) throws CarNotFoundException, CarPartNotFoundException;

    // CarPartId updateNode(CarId carId, CarPartId carPartId) throws CarNotFoundException, CarPartNotFoundException;
    void create(CarId carId, CarPartId carPartId) throws CarPartAlreadyInException;
}
