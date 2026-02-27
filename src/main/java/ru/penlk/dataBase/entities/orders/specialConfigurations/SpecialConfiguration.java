package ru.penlk.dataBase.entities.orders.specialConfigurations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.penlk.dataBase.entities.carParts.CarPartId;
import ru.penlk.dataBase.entities.cars.CarId;

@AllArgsConstructor
@Getter
@Setter
public class SpecialConfiguration {
    private CarId carId;
    private CarPartId carPartId;
    private long price;
}
