package ru.penlk.dataBase.repositories.implementations;

import ru.penlk.dataBase.entities.carParts.CarPartId;
import ru.penlk.dataBase.entities.cars.CarId;
import ru.penlk.dataBase.repositories.interfaces.carParts.CarPartAlreadyInException;
import ru.penlk.dataBase.repositories.interfaces.carParts.CarPartNotFoundException;
import ru.penlk.dataBase.repositories.interfaces.cars.CarNotFoundException;
import ru.penlk.dataBase.repositories.interfaces.orders.commonConfigurations.CommonConfigurationRepository;

import java.util.*;

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
