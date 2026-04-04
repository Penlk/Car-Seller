package ru.penlk.business.internal;

import ru.penlk.business.contracts.DomainValidationException;
import ru.penlk.business.contracts.ServiceException;
import ru.penlk.dao.entities.cars.CarPart;
import ru.penlk.dao.entities.cars.Car;
import ru.penlk.dao.entities.configurations.defaults.DefaultConfiguration;

import java.util.Collection;

public interface RequiredNodeConfigurationService {
    Collection<CarPart> completeRequireNodes(Car car, Collection<CarPart> specialCarParts) throws
            DomainValidationException, ServiceException;
}
