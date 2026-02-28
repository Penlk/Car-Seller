package ru.penlk.businessLayer.implementations;

import lombok.AllArgsConstructor;
import ru.penlk.businessLayer.contracts.ServiceException;
import ru.penlk.businessLayer.contracts.carParts.CarPartService;
import ru.penlk.businessLayer.contracts.carParts.models.CarPartDto;
import ru.penlk.businessLayer.contracts.carParts.models.CreateCarPartDto;
import ru.penlk.dataAcessLayer.repositories.interfaces.carParts.CarPartRepository;

@AllArgsConstructor
public class CommonCarPartService implements CarPartService {
    private final CarPartRepository carPartRepository;

    @Override
    public CarPartDto create(CreateCarPartDto request) {
        return null;
    }

    @Override
    public CarPartDto read(Long id) throws ServiceException {
        return null;
    }

    @Override
    public CarPartDto update(CarPartDto request) throws ServiceException {
        return null;
    }

    @Override
    public void delete(Long id) throws ServiceException {

    }
}
