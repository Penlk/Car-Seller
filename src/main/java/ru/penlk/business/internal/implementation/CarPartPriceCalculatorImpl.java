package ru.penlk.business.internal.implementation;

import lombok.AllArgsConstructor;
import ru.penlk.business.internal.CarPartPriceCalculator;
import ru.penlk.dao.entities.carParts.CarPart;
import ru.penlk.dao.entities.cars.CarId;
import ru.penlk.dao.entities.orders.specialAllowedParts.SpecialAllowedPart;
import ru.penlk.dao.entities.valueObjects.Price;
import ru.penlk.dao.repositories.interfaces.orders.special.configurators.SpecialConfigurationRepository;

import java.util.Collection;

@AllArgsConstructor
public class CarPartPriceCalculatorImpl implements CarPartPriceCalculator {
    private final SpecialConfigurationRepository specialConfigurationRepository;

    @Override
    public Price getSpecialCarPartsPrice(CarId carId, Collection<CarPart> specialCarParts) {
        Collection<SpecialAllowedPart> specialConfigurations = specialConfigurationRepository.findByCarId(carId);

        return specialConfigurations.stream()
                .filter(x ->
                        specialCarParts.stream().anyMatch(y -> x.getCarPartId().equals(y.getId()))
                )
                .map(SpecialAllowedPart::getPrice)
                .reduce(Price.ZERO, Price::add);
    }
}
