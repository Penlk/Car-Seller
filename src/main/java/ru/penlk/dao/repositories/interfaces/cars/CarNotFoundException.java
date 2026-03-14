package ru.penlk.dao.repositories.interfaces.cars;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.penlk.dao.entities.cars.CarId;


@Getter
@AllArgsConstructor
public class CarNotFoundException extends RuntimeException {
    private final CarId id;
}
