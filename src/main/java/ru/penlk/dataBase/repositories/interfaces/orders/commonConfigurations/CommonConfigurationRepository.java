package ru.penlk.dataBase.repositories.interfaces.orders.commonConfigurations;

import ru.penlk.dataBase.entities.cars.CarId;
import ru.penlk.dataBase.entities.carParts.CarPartId;
import ru.penlk.dataBase.repositories.interfaces.cars.CarNotFoundException;
import ru.penlk.dataBase.repositories.interfaces.carParts.CarPartAlreadyInException;
import ru.penlk.dataBase.repositories.interfaces.carParts.CarPartNotFoundException;

import java.util.Collection;

public interface CommonConfigurationRepository {
    Collection<CarPartId> findByCarId(CarId carId) throws CarNotFoundException;

    void delete(CarId carId, CarPartId carPartId) throws CarNotFoundException, CarPartNotFoundException;

    // CarPartId updateNode(CarId carId, CarPartId carPartId) throws CarNotFoundException, CarPartNotFoundException;
    void create(CarId carId, CarPartId carPartId) throws CarPartAlreadyInException;
}
