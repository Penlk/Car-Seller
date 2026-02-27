package ru.penlk.dataBase.repositories.interfaces.carParts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.penlk.dataBase.entities.carParts.CarPartId;

@AllArgsConstructor
@Getter
public class CarPartAlreadyInException extends Exception {
    private final CarPartId id;
}
