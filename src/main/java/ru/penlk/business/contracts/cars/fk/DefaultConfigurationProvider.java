package ru.penlk.business.contracts.cars.fk;

import ru.penlk.dao.entities.cars.Car;
import ru.penlk.dao.entities.configurations.defaults.DefaultConfiguration;
import ru.penlk.dao.repositories.interfaces.cars.CarPartRepository;

import java.util.Set;

public interface DefaultConfigurationProvider {
    Set<DefaultConfiguration> getDefaultConfigurations(Car car, CarPartRepository repository);
}
