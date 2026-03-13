package ru.penlk.dao.repositories.implementations;

import ru.penlk.dao.entities.carParts.CarPartId;
import ru.penlk.dao.entities.cars.CarId;
import ru.penlk.dao.repositories.interfaces.cars.CarNotFoundException;
import ru.penlk.dao.repositories.interfaces.cars.parts.CarPartAlreadyInException;
import ru.penlk.dao.repositories.interfaces.cars.parts.CarPartNotFoundException;
import ru.penlk.dao.repositories.interfaces.orders.common.configurations.CommonConfigurationRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryCommonConfigurationRepository implements CommonConfigurationRepository {
    Map<CarId, List<CarPartId>> nodes = new HashMap<>();

    @Override
    public Collection<CarPartId> findByCarId(CarId carId) throws CarNotFoundException {
        if (!nodes.containsKey(carId)) {
            throw new CarNotFoundException(carId);
        }

        return nodes.get(carId);
    }

    @Override
    public void delete(CarId carId, CarPartId carPartId) throws CarNotFoundException, CarPartNotFoundException {
        if (!nodes.containsKey(carId)) {
            throw new CarNotFoundException(carId);
        }

        List<CarPartId> carParts = nodes.get(carId);

        if (!carParts.removeIf(x -> carPartId.equals(x))) {
            throw new CarPartNotFoundException(carPartId);
        }
    }

    @Override
    public void create(CarId carId, CarPartId carPartId) throws CarPartAlreadyInException {
        if (!nodes.containsKey(carId)) {
            nodes.put(carId, new ArrayList<>());
        }

        List<CarPartId> carParts = nodes.get(carId);

        if (carParts.contains(carPartId)) {
            throw new CarPartAlreadyInException(carPartId);
        }

        carParts.add(carPartId);
    }
}
