package ru.penlk.businessLayer.internal;

import ru.penlk.businessLayer.contracts.DomainValidationException;
import ru.penlk.businessLayer.contracts.IncompatibleComponentException;
import ru.penlk.dataAcessLayer.entities.carParts.CarPart;
import ru.penlk.dataAcessLayer.entities.cars.Car;

import java.util.Collection;

public interface CarPartConfigurationService {
    Collection<CarPart> getCarParts(Car car, Collection<Long> carPartIds) throws
            IncompatibleComponentException, DomainValidationException;
}
