package ru.penlk.businessLayer.implementations;

import lombok.AllArgsConstructor;
import ru.penlk.businessLayer.contracts.ServiceException;
import ru.penlk.businessLayer.contracts.cars.CarService;
import ru.penlk.businessLayer.contracts.cars.models.CarDto;
import ru.penlk.businessLayer.contracts.cars.models.CreateCarDto;
import ru.penlk.dao.entities.cars.Car;
import ru.penlk.dao.entities.cars.CarId;
import ru.penlk.dao.repositories.interfaces.cars.CarNotFoundException;
import ru.penlk.dao.repositories.interfaces.cars.CarRepository;

import java.util.Optional;

@AllArgsConstructor
public class DefaultCarService implements CarService {
    private final CarRepository carRepository;

    @Override
    public CarDto create(CreateCarDto request) {
        Car car = carRepository.create(CreateCarDto.mapToModel(request));

        return CarDto.mapToDto(car);
    }

    @Override
    public CarDto read(Long id) throws ServiceException {
        Optional<Car> carOptional = carRepository.findById(new CarId(id));

        if (carOptional.isPresent()) {
            return CarDto.mapToDto(carOptional.get());
        }

        throw new ServiceException(String.format("Car with id: {%d} not found", id));
    }

    @Override
    public CarDto update(CarDto request) throws ServiceException {
        try {
            Car mappingCar = CarDto.mapToModel(request);

            return CarDto.mapToDto(
                    carRepository.update(mappingCar)
            );
        } catch (CarNotFoundException e) {
            throw new ServiceException(String.format("Car with id: {%d} not found", e.getId().id()));
        }
    }

    @Override
    public void delete(Long id) throws ServiceException {
        try {
            carRepository.delete(new CarId(id));
        } catch (CarNotFoundException e) {
            throw new ServiceException(String.format("Car with id: {%d} not found", id));
        }
    }
}
