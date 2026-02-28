package ru.penlk.dataAcessLayer.entities.orders.commonConfigurations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.penlk.dataAcessLayer.entities.carParts.CarPartId;
import ru.penlk.dataAcessLayer.entities.cars.CarId;

@AllArgsConstructor
@Getter
@Setter
public class CommonConfiguration {
    private CarPartId nodeId;
    private CarId carId;
}
