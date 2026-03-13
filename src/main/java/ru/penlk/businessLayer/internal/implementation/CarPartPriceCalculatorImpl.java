package ru.penlk.businessLayer.internal.implementation;

import lombok.AllArgsConstructor;
import ru.penlk.businessLayer.internal.CarPartPriceCalculator;
import ru.penlk.dataAcessLayer.entities.carParts.CarPart;
import ru.penlk.dataAcessLayer.entities.cars.CarId;
import ru.penlk.dataAcessLayer.entities.orders.specialConfigurations.SpecialConfiguration;
import ru.penlk.dataAcessLayer.entities.valueObjects.Price;
import ru.penlk.dataAcessLayer.repositories.interfaces.orders.specialConfigurations.SpecialConfigurationRepository;

import java.util.Collection;

@AllArgsConstructor
public class CarPartPriceCalculatorImpl implements CarPartPriceCalculator {
    private final SpecialConfigurationRepository specialConfigurationRepository;

    @Override
    public Price getSpecialCarPartsPrice(CarId carId, Collection<CarPart> specialCarParts) {
            Collection<SpecialConfiguration> specialConfigurations = specialConfigurationRepository.findByCarId(carId);

            return specialConfigurations.stream()
                    .filter(x ->
                            specialCarParts.stream().anyMatch(y -> x.getCarPartId().equals(y.getId()))
                    )
                    .map(SpecialConfiguration::getPrice)
                    .reduce(Price.ZERO, Price::add);
    }
}
