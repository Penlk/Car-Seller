package ru.penlk.dao.repositories.interfaces.cars.parts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.penlk.dao.entities.carParts.CarPartId;

@Getter
@AllArgsConstructor
public class CarPartNotFoundException extends RuntimeException {
    private final CarPartId id;
}
