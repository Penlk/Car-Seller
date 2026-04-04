package ru.penlk.business.implementations;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.penlk.business.contracts.ServiceException;
import ru.penlk.business.contracts.cars.CarService;
import ru.penlk.dao.entities.cars.Car;
import ru.penlk.dao.repositories.interfaces.cars.CarRepository;

import java.util.Optional;

@AllArgsConstructor
@Service
@Transactional
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;

    @Override
    public Car create(Car request) {
        return carRepository.save(request);
    }

    @Override
    public Car read(Long id) throws ServiceException {
        Optional<Car> carOptional = carRepository.findById(id);

        if (carOptional.isPresent()) {
            return carOptional.get();
        }

        throw new ServiceException(String.format("Car with id: {%d} not found", id));
    }

    @Override
    public Car update(Car request) throws ServiceException {
        Car car = carRepository.findById(request.getId()).orElseThrow(
                () -> new ServiceException(String.format("Car with id: {%d} not found", request.getId()))
        );

        car.setPrice(request.getPrice());

        car.setBrand(request.getBrand());

        car.setModel(request.getModel());

        car.setBody(request.getBody());

        car.setFuel(request.getFuel());

        car.setEnginePower(request.getEnginePower());

        car.setEngineVolume(request.getEngineVolume());

        car.setGearShiftBox(request.getGearShiftBox());

        car.setCarDrive(request.getCarDrive());

        car.setColour(request.getColour());

        car.setDefaultConfiguration(request.getDefaultConfiguration());

        car.setSpecialAllowedParts(request.getSpecialAllowedParts());

        car.setRequireNodes(request.getRequireNodes());

        return carRepository.save(car);
    }

    @Override
    public void delete(Long id) throws ServiceException {
        carRepository.deleteById(id);
    }
}
