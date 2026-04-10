package ru.penlk.business.internal;

import ru.penlk.business.contracts.DomainValidationException;
import ru.penlk.business.contracts.IncompatibleComponentException;
import ru.penlk.dao.entities.cars.Car;
import ru.penlk.dao.entities.cars.CarPart;

import java.util.Collection;

public interface CarPartConfigurationService {
    Collection<CarPart> getCarParts(Car car, Collection<Long> carPartIds) throws
            IncompatibleComponentException, DomainValidationException;
}
