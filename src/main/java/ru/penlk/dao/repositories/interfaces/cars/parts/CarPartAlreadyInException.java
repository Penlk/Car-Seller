package ru.penlk.dao.repositories.interfaces.cars.parts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.penlk.dao.entities.carParts.CarPartId;

@AllArgsConstructor
@Getter
public class CarPartAlreadyInException extends RuntimeException {
    private final CarPartId id;
}
