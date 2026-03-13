package ru.penlk.dataAcessLayer.repositories.interfaces.carParts;

import ru.penlk.dataAcessLayer.entities.carParts.CarPart;
import ru.penlk.dataAcessLayer.entities.carParts.CarPartId;

import java.util.Collection;
import java.util.Optional;

public interface CarPartRepository {
    Collection<CarPart> findAll();

    Collection<CarPart> query(Collection<CarPartId> ids) throws CarPartNotFoundException;

    Optional<CarPart> findById(CarPartId id);

    void delete(CarPartId id) throws CarPartNotFoundException;

    CarPart update(CarPart node) throws CarPartNotFoundException;

    CarPart create(CarPart node);
}
