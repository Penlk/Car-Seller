package ru.penlk.dataBase.repositories.interfaces.carParts;

import ru.penlk.dataBase.entities.carParts.CarPart;
import ru.penlk.dataBase.entities.carParts.CarPartId;

import java.util.Collection;
import java.util.Optional;

public interface CarPartRepository {
    Collection<CarPart> findAll();
    Optional<CarPart> findById(CarPartId id);
    void delete(CarPartId id) throws CarPartNotFoundException;
    CarPart update(CarPart node) throws CarPartNotFoundException;
    CarPart create(CarPart node) throws CarPartAlreadyInException;
}
