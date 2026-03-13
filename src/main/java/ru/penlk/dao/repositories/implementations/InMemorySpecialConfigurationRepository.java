package ru.penlk.dao.repositories.implementations;

import ru.penlk.dao.entities.carParts.CarPartId;
import ru.penlk.dao.entities.cars.CarId;
import ru.penlk.dao.entities.orders.specialConfigurations.SpecialConfiguration;
import ru.penlk.dao.repositories.interfaces.cars.parts.CarPartAlreadyInException;
import ru.penlk.dao.repositories.interfaces.cars.parts.CarPartNotFoundException;
import ru.penlk.dao.repositories.interfaces.cars.CarNotFoundException;
import ru.penlk.dao.repositories.interfaces.orders.special.configurators.SpecialConfigurationRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemorySpecialConfigurationRepository implements SpecialConfigurationRepository {
    Map<CarId, List<SpecialConfiguration>> nodes = new HashMap<>();

    @Override
    public Collection<SpecialConfiguration> findByCarId(CarId carId) {
        if (!nodes.containsKey(carId)) {
            return new ArrayList<>();
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
