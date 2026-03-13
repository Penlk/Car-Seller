package ru.penlk.dao.repositories.implementations;

import ru.penlk.dao.entities.carParts.CarPart;
import ru.penlk.dao.entities.carParts.CarPartId;
import ru.penlk.dao.repositories.interfaces.cars.parts.CarPartNotFoundException;
import ru.penlk.dao.repositories.interfaces.cars.parts.CarPartRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryCarPartRepository implements CarPartRepository {
    Map<CarPartId, CarPart> carParts = new HashMap<>();
    int lastIndex = 0;

    @Override
    public Collection<CarPart> findAll() {
        return Collections.unmodifiableCollection(carParts.values());
    }

    @Override
    public Optional<CarPart> findById(CarPartId id) {
        return Optional.ofNullable(carParts.get(id));
    }

    @Override
    public void delete(CarPartId id) throws CarPartNotFoundException {
        if (carParts.remove(id) == null) {

            throw new CarPartNotFoundException(id);
        }
    }

    @Override
    public CarPart update(CarPart node) throws CarPartNotFoundException {
        if (!carParts.containsKey(node.getId())) {
            throw new CarPartNotFoundException(node.getId());
        }

        return carParts.get(node.getId());
    }

    @Override
    public CarPart create(CarPart node) {
        ++lastIndex;
        return carParts.put(new CarPartId(lastIndex), node);
    }
}
