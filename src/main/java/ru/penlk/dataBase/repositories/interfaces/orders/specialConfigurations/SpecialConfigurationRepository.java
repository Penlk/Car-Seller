package ru.penlk.dataBase.repositories.interfaces.orders.specialConfigurations;

import ru.penlk.dataBase.entities.cars.CarId;
import ru.penlk.dataBase.entities.orders.specialConfigurations.SpecialConfiguration;
import ru.penlk.dataBase.repositories.interfaces.carParts.CarPartAlreadyInException;
import ru.penlk.dataBase.repositories.interfaces.carParts.CarPartNotFoundException;
import ru.penlk.dataBase.repositories.interfaces.cars.CarNotFoundException;

import java.util.Collection;

public interface SpecialConfigurationRepository {
    Collection<SpecialConfiguration> findByCarId(CarId carId) throws CarNotFoundException;

    void delete(SpecialConfiguration configuration) throws CarNotFoundException, CarPartNotFoundException;

    // CarPartId updateNode(SpecialConfiguration configuration) throws CarNotFoundException, CarPartNotFoundException;
    void create(SpecialConfiguration configuration) throws CarPartAlreadyInException;
}
