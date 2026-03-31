package ru.penlk.dao.repositories.interfaces.cars;

import ru.penlk.dao.entities.cars.Car;

import java.util.Collection;
import java.util.Optional;

public interface CarRepository {
    Collection<Car> query(CarQuery query);

    Optional<Car> findById(CarId id);

    void delete(CarId id) throws CarNotFoundException;

    Car update(Car car) throws CarNotFoundException;

    Car create(Car car) throws CarAlreadyInException;
}
