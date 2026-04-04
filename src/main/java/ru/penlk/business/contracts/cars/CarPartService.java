package ru.penlk.business.contracts.cars.parts;

import ru.penlk.business.contracts.ServiceException;
import ru.penlk.business.contracts.cars.parts.models.CarPartDto;
import ru.penlk.business.contracts.cars.parts.models.CreateCarPartDto;

public interface CarPartService {
    CarPartDto create(CreateCarPartDto request);

    CarPartDto read(Long id) throws ServiceException;

    CarPartDto update(CarPartDto request) throws ServiceException;

    void delete(Long id) throws ServiceException;
}
