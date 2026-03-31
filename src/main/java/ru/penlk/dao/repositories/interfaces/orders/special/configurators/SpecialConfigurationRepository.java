package ru.penlk.dao.repositories.interfaces.orders.special.configurators;

import ru.penlk.dao.entities.orders.special.SpecialAllowedPart;
import ru.penlk.dao.repositories.interfaces.cars.CarNotFoundException;
import ru.penlk.dao.repositories.interfaces.cars.parts.CarPartAlreadyInException;
import ru.penlk.dao.repositories.interfaces.cars.parts.CarPartNotFoundException;

import java.util.Collection;

public interface SpecialConfigurationRepository {
    Collection<SpecialAllowedPart> findByCarId(CarId carId);

    void delete(SpecialAllowedPart configuration) throws CarNotFoundException, CarPartNotFoundException;

    // CarPartId updateNode(SpecialConfiguration configuration) throws CarNotFoundException, CarPartNotFoundException;
    void create(SpecialAllowedPart configuration) throws CarPartAlreadyInException;
}
