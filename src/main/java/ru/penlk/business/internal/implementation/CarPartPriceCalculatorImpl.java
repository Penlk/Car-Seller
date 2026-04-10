package ru.penlk.business.internal.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.penlk.business.internal.CarPartPriceCalculator;
import ru.penlk.dao.entities.orders.special.SpecialAllowedPart;
import ru.penlk.dao.entities.vo.Price;

import java.util.Collection;

@AllArgsConstructor
@Service
public class CarPartPriceCalculatorImpl implements CarPartPriceCalculator {
    @Override
    public Price getSpecialCarPartsPrice(Collection<SpecialAllowedPart> specialAllowedParts) {
        return specialAllowedParts.stream()
                .map(SpecialAllowedPart::getPrice)
                .reduce(Price.ZERO, Price::add);
    }
}
