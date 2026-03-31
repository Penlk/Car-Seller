package ru.penlk.dao.repositories.interfaces.cars.parts;

import ru.penlk.dao.entities.cars.CarPart;

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
