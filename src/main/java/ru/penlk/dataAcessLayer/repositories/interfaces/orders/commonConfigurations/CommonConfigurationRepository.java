package ru.penlk.dataAcessLayer.repositories.interfaces.orders.commonConfigurations;

import ru.penlk.dataAcessLayer.entities.carParts.CarPartId;
import ru.penlk.dataAcessLayer.entities.cars.CarId;
import ru.penlk.dataAcessLayer.repositories.interfaces.carParts.CarPartAlreadyInException;
import ru.penlk.dataAcessLayer.repositories.interfaces.carParts.CarPartNotFoundException;
import ru.penlk.dataAcessLayer.repositories.interfaces.cars.CarNotFoundException;

import java.util.Collection;

public interface CommonConfigurationRepository {
    Collection<CarPartId> findByCarId(CarId carId) throws CarNotFoundException;

    void delete(CarId carId, CarPartId carPartId) throws CarNotFoundException, CarPartNotFoundException;

    // CarPartId updateNode(CarId carId, CarPartId carPartId) throws CarNotFoundException, CarPartNotFoundException;
    void create(CarId carId, CarPartId carPartId) throws CarPartAlreadyInException;
}
