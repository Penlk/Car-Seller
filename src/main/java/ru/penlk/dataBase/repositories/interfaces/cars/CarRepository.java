package ru.penlk.dataBase.repositories.interfaces.cars;

import ru.penlk.dataBase.entities.cars.Car;
import ru.penlk.dataBase.entities.cars.CarId;

import java.util.Collection;
import java.util.Optional;

public interface CarRepository {
    Collection<Car> query(CarQuery query);

    Optional<Car> findById(CarId id);

    void delete(CarId id) throws CarNotFoundException;

    Car update(Car car) throws CarNotFoundException;

    Car create(Car car) throws CarAlreadyInException;
}
