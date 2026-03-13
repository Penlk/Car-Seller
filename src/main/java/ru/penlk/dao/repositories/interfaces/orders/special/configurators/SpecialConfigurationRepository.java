package ru.penlk.dao.repositories.interfaces.orders.special.configurators;

import ru.penlk.dao.entities.cars.CarId;
import ru.penlk.dao.entities.orders.specialConfigurations.SpecialConfiguration;
import ru.penlk.dao.repositories.interfaces.cars.CarNotFoundException;
import ru.penlk.dao.repositories.interfaces.cars.parts.CarPartAlreadyInException;
import ru.penlk.dao.repositories.interfaces.cars.parts.CarPartNotFoundException;

import java.util.Collection;

public interface SpecialConfigurationRepository {
    Collection<SpecialConfiguration> findByCarId(CarId carId);

    void delete(SpecialConfiguration configuration) throws CarNotFoundException, CarPartNotFoundException;

    // CarPartId updateNode(SpecialConfiguration configuration) throws CarNotFoundException, CarPartNotFoundException;
    void create(SpecialConfiguration configuration) throws CarPartAlreadyInException;
}
