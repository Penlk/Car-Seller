package ru.penlk.dataAcessLayer.repositories.interfaces.cars;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.penlk.dataAcessLayer.entities.cars.CarId;


@Getter
@AllArgsConstructor
public class CarNotFoundException extends RuntimeException {
    private final CarId id;
}
