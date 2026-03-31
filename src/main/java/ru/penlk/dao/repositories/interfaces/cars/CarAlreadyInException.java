package ru.penlk.dao.repositories.interfaces.cars;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CarAlreadyInException extends RuntimeException {
    private final CarId carId;
}
