package ru.penlk.businessLayer.contracts.cars;

import ru.penlk.businessLayer.contracts.ServiceException;
import ru.penlk.businessLayer.contracts.cars.models.CarDto;
import ru.penlk.businessLayer.contracts.cars.models.CreateCarDto;

public interface CarService {
    CarDto create(CreateCarDto request);

    CarDto read(Long id) throws ServiceException;

    CarDto update(CarDto request) throws ServiceException;

    void delete(Long id) throws ServiceException;
}
