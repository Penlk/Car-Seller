package ru.penlk.dao.repositories.interfaces.cars;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class CarNotFoundException extends RuntimeException {
    private final CarId id;
}
