package ru.penlk.dataAcessLayer.repositories.interfaces.carParts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.penlk.dataAcessLayer.entities.carParts.CarPartId;

@Getter
@AllArgsConstructor
public class CarPartNotFoundException extends RuntimeException {
    private final CarPartId id;
}
