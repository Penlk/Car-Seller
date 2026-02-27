package ru.penlk.dataBase.entities.orders.commonConfigurations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.penlk.dataBase.entities.carParts.CarPartId;
import ru.penlk.dataBase.entities.cars.CarId;

@AllArgsConstructor
@Getter
@Setter
public class CommonConfiguration {
    private CarPartId nodeId;
    private CarId carId;
}
