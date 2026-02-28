package ru.penlk.dataAcessLayer.repositories.implementations;

import ru.penlk.dataAcessLayer.entities.cars.Car;
import ru.penlk.dataAcessLayer.entities.cars.CarId;
import ru.penlk.dataAcessLayer.repositories.interfaces.cars.CarNotFoundException;
import ru.penlk.dataAcessLayer.repositories.interfaces.cars.CarQuery;
import ru.penlk.dataAcessLayer.repositories.interfaces.cars.CarRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryCarRepository implements CarRepository {
    Map<CarId, Car> cars = new HashMap<>();
    int lastIndex = 0;


    @Override
    public Collection<Car> query(CarQuery query) {
        return cars.values().stream()
                .filter(car -> query.getMinPrice() == null || car.getPrice().compareTo(query.getMinPrice()) >= 0)
                .filter(car -> query.getMaxPrice() == null || car.getPrice().compareTo(query.getMaxPrice()) <= 0)
                .filter(car -> query.getBrands() == null || query.getBrands().contains(car.getBrand()))
                .filter(car -> query.getModel() == null || car.getModel().equals(query.getModel()))
                .filter(car -> query.getBodies() == null || query.getBodies().contains(car.getBody()))
                .filter(car -> query.getFuels() == null || query.getFuels().contains(car.getFuel()))
                .filter(car -> query.getMinEnginePower() == null || car.getEnginePower().compareTo(query.getMinEnginePower()) >= 0)
                .filter(car -> query.getMaxEnginePower() == null || car.getEnginePower().compareTo(query.getMaxEnginePower()) <= 0)
                .filter(car -> query.getMinEngineVolume() == null || car.getEngineVolume().compareTo(query.getMinEngineVolume()) >= 0)
                .filter(car -> query.getMaxEngineVolume() == null || car.getEngineVolume().compareTo(query.getMaxEngineVolume()) <= 0)
                .filter(car -> query.getGearShiftBoxes() == null || query.getGearShiftBoxes().contains(car.getGearShiftBox()))
                .filter(car -> query.getCarDrives() == null || query.getCarDrives().contains(car.getCarDrive()))
                .filter(car -> query.getColours() == null || query.getColours().contains(car.getColour()))
                .toList();
    }

    @Override
    public Optional<Car> findById(CarId id) {
        return Optional.ofNullable(cars.get(id));
    }

    @Override
    public void delete(CarId id) throws CarNotFoundException {
        if (cars.remove(id) == null) {

            throw new CarNotFoundException(id);
        }
    }

    @Override
    public Car update(Car node) throws CarNotFoundException {
        if (!cars.containsKey(node.getId())) {
            throw new CarNotFoundException(node.getId());
        }

        return cars.get(node.getId());
    }

    @Override
    public Car create(Car node) {
        ++lastIndex;
        return cars.put(new CarId(lastIndex), node);
    }
}
