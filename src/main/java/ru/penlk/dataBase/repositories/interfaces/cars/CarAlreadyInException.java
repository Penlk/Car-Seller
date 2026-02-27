package ru.penlk.dataBase.repositories.interfaces.cars;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.penlk.dataBase.entities.cars.CarId;

@AllArgsConstructor
@Getter
public class CarAlreadyInException extends RuntimeException {
    private final CarId carId;
}
