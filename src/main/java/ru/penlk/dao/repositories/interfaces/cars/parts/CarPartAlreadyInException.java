package ru.penlk.dao.repositories.interfaces.cars.parts;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CarPartAlreadyInException extends RuntimeException {
    private final CarPartId id;
}
