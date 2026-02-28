package ru.penlk.businessLayer.contracts.carParts;

import ru.penlk.businessLayer.contracts.ServiceException;
import ru.penlk.businessLayer.contracts.carParts.models.CarPartDto;
import ru.penlk.businessLayer.contracts.carParts.models.CreateCarPartDto;

public interface CarPartService {
    CarPartDto create(CreateCarPartDto request);

    CarPartDto read(Long id) throws ServiceException;

    CarPartDto update(CarPartDto request) throws ServiceException;

    void delete(Long id) throws ServiceException;
}
