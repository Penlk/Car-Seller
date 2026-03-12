package ru.penlk.dataAcessLayer.repositories.interfaces.carParts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.penlk.dataAcessLayer.entities.carParts.CarPartId;

@AllArgsConstructor
@Getter
public class CarPartAlreadyInException extends RuntimeException {
    private final CarPartId id;
}
