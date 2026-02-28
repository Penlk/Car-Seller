package ru.penlk.dataAcessLayer.repositories.interfaces.orders.specialConfigurations;

import ru.penlk.dataAcessLayer.entities.cars.CarId;
import ru.penlk.dataAcessLayer.entities.orders.specialConfigurations.SpecialConfiguration;
import ru.penlk.dataAcessLayer.repositories.interfaces.carParts.CarPartAlreadyInException;
import ru.penlk.dataAcessLayer.repositories.interfaces.carParts.CarPartNotFoundException;
import ru.penlk.dataAcessLayer.repositories.interfaces.cars.CarNotFoundException;

import java.util.Collection;

public interface SpecialConfigurationRepository {
    Collection<SpecialConfiguration> findByCarId(CarId carId) throws CarNotFoundException;

    void delete(SpecialConfiguration configuration) throws CarNotFoundException, CarPartNotFoundException;

    // CarPartId updateNode(SpecialConfiguration configuration) throws CarNotFoundException, CarPartNotFoundException;
    void create(SpecialConfiguration configuration) throws CarPartAlreadyInException;
}
