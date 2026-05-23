package ru.penlk.business.contracts.cars;

import ru.penlk.dao.entities.cars.Car;

import java.util.Set;

public interface CarStorageService {
    Set<Car> getAllAvailableCars();

    Car getById(Long carId);
}