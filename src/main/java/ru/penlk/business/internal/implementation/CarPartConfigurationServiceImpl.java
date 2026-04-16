package ru.penlk.business.internal.implementation;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.penlk.business.contracts.IncompatibleComponentException;
import ru.penlk.business.contracts.ServiceException;
import ru.penlk.business.internal.CarPartConfigurationService;
import ru.penlk.dao.entities.cars.Car;
import ru.penlk.dao.entities.cars.CarPart;
import ru.penlk.dao.entities.orders.special.SpecialAllowedPart;
import ru.penlk.dao.repositories.interfaces.cars.CarPartRepository;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@Service
@Transactional
public class CarPartConfigurationServiceImpl implements CarPartConfigurationService {
    private final CarPartRepository carPartRepository;

    @Override
    public Collection<CarPart> getCarParts(Car car, Collection<Long> carPartIds) {
        Collection<CarPart> carParts;

        carParts = carPartRepository.findAllById(carPartIds);

        if (carParts.size() != carPartIds.size()) {
            throw new ServiceException("Cannot find all car parts");
        }

        checkIncompatibleParts(car, carParts);

        validateUniqueNodeIds(car, carParts);

        return carParts;
    }

    private void checkIncompatibleParts(Car car, Collection<CarPart> carParts) {
        Collection<SpecialAllowedPart> specialConfigurations = car.getSpecialAllowedParts();

        List<CarPart> wrongCarParts = carParts.stream()
                .filter(x ->
                        specialConfigurations.stream().noneMatch(y -> x.getId().equals(y.getCarPart().getId())))
                .toList();

        if (!wrongCarParts.isEmpty()) {
            throw new IncompatibleComponentException("Wrong car's part: " + wrongCarParts);
        }
    }

    private void validateUniqueNodeIds(Car car, Collection<CarPart> carParts) {
        long countUniqueNodeIds = carParts.stream()
                .map(CarPart::getNode)
                .distinct()
                .count();

        if (countUniqueNodeIds != carParts.size()) {
            throw new ServiceException(String.format("Duplicated node ids found for given car: %s", car.getId()));
        }
    }
}
