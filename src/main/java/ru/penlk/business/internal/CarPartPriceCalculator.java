package ru.penlk.business.internal;

import ru.penlk.business.contracts.ServiceException;
import ru.penlk.dao.entities.cars.CarPart;
import ru.penlk.dao.entities.vo.Price;

import java.util.Collection;

public interface CarPartPriceCalculator {
    Price getSpecialCarPartsPrice(CarId carId, Collection<CarPart> specialCarParts) throws ServiceException;
}
