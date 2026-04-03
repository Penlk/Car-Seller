package ru.penlk.business.implementations;

import lombok.AllArgsConstructor;
import ru.penlk.business.contracts.ServiceException;
import ru.penlk.business.contracts.cars.parts.CarPartService;
import ru.penlk.business.contracts.cars.parts.models.CarPartDto;
import ru.penlk.business.contracts.cars.parts.models.CreateCarPartDto;
import ru.penlk.dao.entities.cars.CarPart;
import ru.penlk.dao.repositories.interfaces.cars.CarPartRepository;

import java.util.Optional;

@AllArgsConstructor
public class CarPartServiceImpl implements CarPartService {
    private final CarPartRepository carPartRepository;

    @Override
    public CarPartDto create(CreateCarPartDto request) {
        CarPart carPart = carPartRepository.create(new CarPart(
                CarPartId.defaultId(),
                request.namePart(),
                new NodeId(request.nodeId())
        ));

        return CarPartDto.MapToDto(carPart);
    }

    @Override
    public CarPartDto read(Long id) throws ServiceException {
        Optional<CarPart> carPartOptional = carPartRepository.findById(new CarPartId(id));

        if (carPartOptional.isPresent()) {
            return CarPartDto.MapToDto(carPartOptional.get());
        }

        throw new ServiceException(String.format("CarPart with id: {%d} not found", id));
    }

    @Override
    public CarPartDto update(CarPartDto request) throws ServiceException {
        try {
            CarPart mappingCarPart = new CarPart(
                    new CarPartId(request.id()),
                    request.namePart(),
                    new NodeId(request.nodeId()));

            return CarPartDto.MapToDto(
                    carPartRepository.update(mappingCarPart)
            );
        } catch (CarPartNotFoundException e) {
            throw new ServiceException(String.format("CarPart with id: {%d} not found", e.getId().id()));
        }
    }

    @Override
    public void delete(Long id) throws ServiceException {
        try {
            carPartRepository.delete(new CarPartId(id));
        } catch (CarPartNotFoundException e) {
            throw new ServiceException(String.format("CarPart with id: {%d} not found", id));
        }
    }
}
