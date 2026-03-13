package ru.penlk.dao.entities.orders.specialConfigurations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.penlk.dao.entities.carParts.CarPartId;
import ru.penlk.dao.entities.cars.CarId;
import ru.penlk.dao.entities.valueObjects.Price;

@AllArgsConstructor
@Getter
@Setter
public class SpecialConfiguration {
    private CarId carId;
    private CarPartId carPartId;
    private Price price;
}
