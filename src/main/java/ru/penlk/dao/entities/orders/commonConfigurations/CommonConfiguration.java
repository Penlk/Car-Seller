package ru.penlk.dao.entities.orders.commonConfigurations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.penlk.dao.entities.carParts.CarPartId;
import ru.penlk.dao.entities.cars.CarId;

@AllArgsConstructor
@Getter
@Setter
public class CommonConfiguration {
    private CarPartId nodeId;
    private CarId carId;
}
