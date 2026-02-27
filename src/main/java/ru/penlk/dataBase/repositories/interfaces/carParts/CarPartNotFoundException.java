package ru.penlk.dataBase.repositories.interfaces.carParts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.penlk.dataBase.entities.carParts.CarPartId;

@Getter
@AllArgsConstructor
public class CarPartNotFoundException extends RuntimeException {
    private final CarPartId id;
}
