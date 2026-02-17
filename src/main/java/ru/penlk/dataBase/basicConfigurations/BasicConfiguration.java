package ru.penlk.dataBase.basicConfigurations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.penlk.dataBase.carParts.CarPartId;
import ru.penlk.dataBase.cars.CarId;

@AllArgsConstructor
@Getter
@Setter
public class BasicConfiguration {
    private BasicConfigurationId id;
    private CarPartId carPartId;
    private CarId carId;
}
