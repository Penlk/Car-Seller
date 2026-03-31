package ru.penlk.business.internal.implementation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.penlk.dao.entities.carParts.CarPart;
import ru.penlk.dao.entities.carParts.CarPartId;
import ru.penlk.dao.entities.cars.CarId;
import ru.penlk.dao.entities.orders.specialAllowedParts.SpecialAllowedPart;
import ru.penlk.dao.entities.valueObjects.Price;
import ru.penlk.dao.repositories.interfaces.orders.special.configurators.SpecialConfigurationRepository;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarPartPriceCalculatorImplTest {
    @Mock
    SpecialConfigurationRepository specialConfigurationRepository;

    @InjectMocks
    CarPartPriceCalculatorImpl calculator;

    @Test
    void sumsMatchedPrices() {
        CarId carId = new CarId(1);
        CarPart p1 = new CarPart(new CarPartId(1), "p1", null);
        CarPart p2 = new CarPart(new CarPartId(2), "p2", null);

        SpecialAllowedPart s1 = new SpecialAllowedPart(carId, new CarPartId(1), new Price(new BigDecimal("10")));
        SpecialAllowedPart s2 = new SpecialAllowedPart(carId, new CarPartId(3), new Price(new BigDecimal("5")));

        when(specialConfigurationRepository.findByCarId(carId)).thenReturn(List.of(s1, s2));

        Price total = calculator.getSpecialCarPartsPrice(carId, List.of(p1, p2));

        assertEquals(new Price(new BigDecimal("10")), total);
    }
}
