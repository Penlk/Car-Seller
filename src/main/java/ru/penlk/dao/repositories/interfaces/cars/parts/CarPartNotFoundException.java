package ru.penlk.dao.repositories.interfaces.cars.parts;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CarPartNotFoundException extends RuntimeException {
    private final CarPartId id;
}
