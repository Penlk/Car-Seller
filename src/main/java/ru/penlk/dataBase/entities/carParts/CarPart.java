package ru.penlk.dataBase.entities.carParts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CarPart {
    private CarPartId id;
    private String nameNode;
    private String namePart;
}
