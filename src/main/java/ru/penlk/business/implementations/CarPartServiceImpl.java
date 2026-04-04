package ru.penlk.business.implementations;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.penlk.business.contracts.ServiceException;
import ru.penlk.business.contracts.cars.CarPartService;
import ru.penlk.dao.entities.cars.CarPart;
import ru.penlk.dao.repositories.interfaces.cars.CarPartRepository;

import java.util.Optional;

@AllArgsConstructor
@Service
@Transactional
public class CarPartServiceImpl implements CarPartService {
    private final CarPartRepository carPartRepository;

    @Override
    public CarPart create(CarPart request) {
        return carPartRepository.save(request);
    }

    @Override
    public CarPart read(Long id) throws ServiceException {
        Optional<CarPart> carPartOptional = carPartRepository.findById(id);

        if (carPartOptional.isPresent()) {
            return carPartOptional.get();
        }

        throw new ServiceException(String.format("CarPart with id: {%d} not found", id));
    }

    @Override
    public CarPart update(CarPart request) throws ServiceException {
        CarPart carPart = carPartRepository.findById(request.getId()).orElseThrow(
                () -> new ServiceException(String.format("CarPart with id: {%d} not found", request.getId()))
        );

        carPart.setNamePart(request.getNamePart());
        carPart.setNode(request.getNode());

        return carPartRepository.save(carPart);
    }

    @Override
    public void delete(Long carPartId) throws ServiceException {
        carPartRepository.deleteById(carPartId);
    }
}
