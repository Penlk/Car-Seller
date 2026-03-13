package ru.penlk.businessLayer.internal.implementation;

import lombok.AllArgsConstructor;
import ru.penlk.businessLayer.contracts.IncompatibleComponentException;
import ru.penlk.businessLayer.contracts.ServiceException;
import ru.penlk.businessLayer.internal.CarPartConfigurationService;
import ru.penlk.dataAcessLayer.entities.carParts.CarPart;
import ru.penlk.dataAcessLayer.entities.carParts.CarPartId;
import ru.penlk.dataAcessLayer.entities.cars.Car;
import ru.penlk.dataAcessLayer.entities.orders.specialConfigurations.SpecialConfiguration;
import ru.penlk.dataAcessLayer.entities.orders.specialOrder.SpecialOrderId;
import ru.penlk.dataAcessLayer.repositories.interfaces.carParts.CarPartAlreadyInException;
import ru.penlk.dataAcessLayer.repositories.interfaces.carParts.CarPartNotFoundException;
import ru.penlk.dataAcessLayer.repositories.interfaces.carParts.CarPartRepository;
import ru.penlk.dataAcessLayer.repositories.interfaces.orders.specialConfigurations.SpecialConfigurationRepository;

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
        Collection<SpecialConfiguration> specialConfigurations = specialConfigurationRepository.findByCarId(car.getId());

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
                .map(CarPart::getNodeId)
                .distinct()
                .count();

        if (countUniqueNodeIds != carParts.size()) {
            throw new ServiceException(String.format("Duplicated node ids found for given car: %s", car.getId()));
        }
    }
}
