package ru.penlk.business.internal;

import ru.penlk.business.contracts.ServiceException;
import ru.penlk.dao.entities.carParts.CarPart;
import ru.penlk.dao.entities.cars.CarId;
import ru.penlk.dao.entities.valueObjects.Price;

import java.util.Collection;

public interface CarPartPriceCalculator {
    Price getSpecialCarPartsPrice(CarId carId, Collection<CarPart> specialCarParts) throws ServiceException;
}
