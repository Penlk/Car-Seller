package ru.penlk.dataBase.repositories.interfaces.cars;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.penlk.dataBase.entities.cars.CarId;


@Getter
@AllArgsConstructor
public class CarNotFoundException extends RuntimeException {
    private final CarId carId;
}
