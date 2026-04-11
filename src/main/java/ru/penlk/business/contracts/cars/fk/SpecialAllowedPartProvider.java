package ru.penlk.business.contracts.cars.fk;

import ru.penlk.dao.entities.cars.Car;
import ru.penlk.dao.entities.orders.special.SpecialAllowedPart;
import ru.penlk.dao.repositories.interfaces.cars.CarPartRepository;

import java.util.Set;

public interface SpecialAllowedPartProvider {
    Set<SpecialAllowedPart> getSpecialAllowedParts(Car car, CarPartRepository repository);
}
