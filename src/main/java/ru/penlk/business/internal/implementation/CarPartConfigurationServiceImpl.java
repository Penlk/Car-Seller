package ru.penlk.business.internal.implementation;

import lombok.AllArgsConstructor;
import ru.penlk.business.contracts.IncompatibleComponentException;
import ru.penlk.business.contracts.ServiceException;
import ru.penlk.business.internal.CarPartConfigurationService;
import ru.penlk.dao.entities.cars.CarPart;
import ru.penlk.dao.entities.cars.Car;
import ru.penlk.dao.entities.orders.special.SpecialAllowedPart;
import ru.penlk.dao.repositories.interfaces.cars.CarPartRepository;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public class CarPartConfigurationServiceImpl implements CarPartConfigurationService {
    private final CarPartRepository carPartRepository;
    private final SpecialConfigurationRepository specialConfigurationRepository;

    @Override
    public Collection<CarPart> getCarParts(Car car, Collection<Long> carPartIds) {
        Collection<CarPart> carParts;

        try {
            carParts = carPartRepository.query(
                    carPartIds.stream().map(CarPartId::new).toList()
            );
        } catch (CarPartNotFoundException e) {
            throw new ServiceException(e.getMessage());
        }

        checkIncompatibleParts(car, carParts);

        validateUniqueNodeIds(car, carParts);

        return carParts;
    }

    private void checkIncompatibleParts(Car car, Collection<CarPart> carParts) {
        Collection<SpecialAllowedPart> specialConfigurations = specialConfigurationRepository.findByCarId(car.getId());

        List<CarPart> wrongCarParts = carParts.stream()
                .filter(x ->
                        specialConfigurations.stream().noneMatch(y -> x.getId().equals(y.getCarPartId())))
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
