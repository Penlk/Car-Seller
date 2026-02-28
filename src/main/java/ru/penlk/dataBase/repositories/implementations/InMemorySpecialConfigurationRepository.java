package ru.penlk.dataBase.repositories.implementations;

import ru.penlk.dataBase.entities.carParts.CarPartId;
import ru.penlk.dataBase.entities.cars.CarId;
import ru.penlk.dataBase.entities.orders.specialConfigurations.SpecialConfiguration;
import ru.penlk.dataBase.repositories.interfaces.carParts.CarPartAlreadyInException;
import ru.penlk.dataBase.repositories.interfaces.carParts.CarPartNotFoundException;
import ru.penlk.dataBase.repositories.interfaces.cars.CarNotFoundException;
import ru.penlk.dataBase.repositories.interfaces.orders.commonConfigurations.CommonConfigurationRepository;
import ru.penlk.dataBase.repositories.interfaces.orders.specialConfigurations.SpecialConfigurationRepository;

import java.util.*;

public class InMemorySpecialConfigurationRepository implements SpecialConfigurationRepository {
    Map<CarId, List<SpecialConfiguration>> nodes = new HashMap<>();

    @Override
    public Collection<SpecialConfiguration> findByCarId(CarId carId) throws CarNotFoundException {
        if (!nodes.containsKey(carId)) {
            throw new CarNotFoundException(carId);
        }

        return nodes.get(carId);
    }

    @Override
    public void delete(SpecialConfiguration configuration) throws CarNotFoundException, CarPartNotFoundException {
        if (!nodes.containsKey(configuration.getCarId())) {
            throw new CarNotFoundException(configuration.getCarId());
        }

        List<SpecialConfiguration> configurations = nodes.get(configuration.getCarId());

        if (!configurations.removeIf(x -> configuration.getCarPartId().equals(x.getCarPartId()))) {
            throw new CarPartNotFoundException(configuration.getCarPartId());
        }
    }

    @Override
    public void create(SpecialConfiguration configuration) throws CarPartAlreadyInException {
        CarId carId = configuration.getCarId();
        CarPartId carPartId = configuration.getCarPartId();

        if (!nodes.containsKey(carId)) {
            nodes.put(carId, new ArrayList<>());
        }

        List<SpecialConfiguration> configurations = nodes.get(carId);

        if (configurations.contains(configuration)) {
            throw new CarPartAlreadyInException(carPartId);
        }

        configurations.add(configuration);
    }
}
