package ru.penlk.business.internal;

import ru.penlk.business.contracts.ServiceException;
import ru.penlk.dao.entities.orders.special.SpecialAllowedPart;
import ru.penlk.dao.entities.vo.Price;

import java.util.Collection;

public interface CarPartPriceCalculator {
    Price getSpecialCarPartsPrice(Collection<SpecialAllowedPart> specialAllowedParts) throws ServiceException;
}
