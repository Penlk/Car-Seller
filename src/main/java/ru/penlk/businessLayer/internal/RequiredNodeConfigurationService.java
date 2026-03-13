package ru.penlk.businessLayer.internal;

import ru.penlk.businessLayer.contracts.DomainValidationException;
import ru.penlk.businessLayer.contracts.ServiceException;
import ru.penlk.dataAcessLayer.entities.carParts.CarPart;
import ru.penlk.dataAcessLayer.entities.cars.Car;

import java.util.Collection;

public interface RequiredNodeConfigurationService {
    Collection<CarPart> completeRequireNodes(Car car, Collection<CarPart> specialCarParts) throws
            DomainValidationException, ServiceException;
}
