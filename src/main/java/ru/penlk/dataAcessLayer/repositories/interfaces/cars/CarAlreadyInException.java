package ru.penlk.dataAcessLayer.repositories.interfaces.cars;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.penlk.dataAcessLayer.entities.cars.CarId;

@AllArgsConstructor
@Getter
public class CarAlreadyInException extends RuntimeException {
    private final CarId carId;
}
