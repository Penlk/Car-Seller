package ru.penlk.businessLayer.implementations;

import lombok.AllArgsConstructor;
import ru.penlk.businessLayer.contracts.ServiceException;
import ru.penlk.businessLayer.contracts.cars.CarService;
import ru.penlk.businessLayer.contracts.cars.models.CarDto;
import ru.penlk.businessLayer.contracts.cars.models.CreateCarDto;
import ru.penlk.dataAcessLayer.entities.cars.Car;
import ru.penlk.dataAcessLayer.entities.cars.CarId;
import ru.penlk.dataAcessLayer.repositories.interfaces.cars.CarNotFoundException;
import ru.penlk.dataAcessLayer.repositories.interfaces.cars.CarRepository;

import java.util.Optional;

@AllArgsConstructor
public class CommonCarService implements CarService {
    private final CarRepository carRepository;

    @Override
    public CarDto create(CreateCarDto request) {
        Car car = carRepository.create(CreateCarDto.MapToCar(request));

        return CarDto.MapToDto(car);
    }

    @Override
    public CarDto read(Long id) throws ServiceException {
        Optional<Car> carOptional = carRepository.findById(new CarId(id));

        if (carOptional.isPresent()) {
            return CarDto.MapToDto(carOptional.get());
        }

        throw new ServiceException("Car not found");
    }

    @Override
    public CarDto update(CarDto request) throws ServiceException {
        try {
            Car mappingCar = CarDto.MapToCar(request);

            return CarDto.MapToDto(
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
