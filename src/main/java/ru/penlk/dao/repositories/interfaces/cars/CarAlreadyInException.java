package ru.penlk.dao.repositories.interfaces.cars;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.penlk.dao.entities.cars.CarId;

@AllArgsConstructor
@Getter
public class CarAlreadyInException extends RuntimeException {
    private final CarId carId;
}
