package ru.penlk.businessLayer.internal;

import ru.penlk.businessLayer.contracts.ServiceException;
import ru.penlk.dataAcessLayer.entities.carParts.CarPart;
import ru.penlk.dataAcessLayer.entities.cars.CarId;
import ru.penlk.dataAcessLayer.entities.valueObjects.Price;

import java.util.Collection;

public interface CarPartPriceCalculator {
    Price getSpecialCarPartsPrice(CarId carId, Collection<CarPart> specialCarParts) throws ServiceException;
}
