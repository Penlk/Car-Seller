package ru.penlk.business.internal.implementation;

import lombok.AllArgsConstructor;
import ru.penlk.business.contracts.DomainValidationException;
import ru.penlk.business.internal.RequiredNodeConfigurationService;
import ru.penlk.dao.entities.cars.CarPart;
import ru.penlk.dao.entities.cars.Car;
import ru.penlk.dao.repositories.interfaces.cars.parts.CarPartRepository;
import ru.penlk.dao.repositories.interfaces.nodes.require.RequireNodeRepository;
import ru.penlk.dao.repositories.interfaces.orders.common.configurations.CommonConfigurationRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public class RequiredNodeConfigurationServiceImpl implements RequiredNodeConfigurationService {
    private final CarPartRepository carPartRepository;
    private final CommonConfigurationRepository commonConfigurationRepository;
    private final RequireNodeRepository requireNodeRepository;

    @Override
    public Collection<CarPart> completeRequireNodes(Car car, Collection<CarPart> specialCarParts) {
        List<NodeId> missingNodeIds = getRequireNodeIds(car, specialCarParts);

        return tryToCompleteRequireNodes(car, missingNodeIds);
    }

    private List<CarPart> tryToCompleteRequireNodes(Car car, Collection<NodeId> missingNodeIds) {
        Collection<CarPartId> commonConfigurationCarPartIds = commonConfigurationRepository.findByCarId(car.getId());

        List<NodeId> copyMissingNodeIds = new ArrayList<>(missingNodeIds.stream().toList());

        List<CarPart> completedCarParts = carPartRepository.findAll().stream()
                .filter(x -> commonConfigurationCarPartIds.contains(x.getId()))
                .filter(x -> copyMissingNodeIds.contains(x.getNode()))
                .peek(x -> copyMissingNodeIds.remove(x.getNode()))
                .toList();

        if (!copyMissingNodeIds.isEmpty()) {
            throw new DomainValidationException("Missing required node ids: " + copyMissingNodeIds);
        }

        return completedCarParts;
    }

    private List<NodeId> getRequireNodeIds(Car car, Collection<CarPart> specialCarParts) {
        Collection<NodeId> requireNodeIds = requireNodeRepository.findByCarId(car.getId());

        List<NodeId> nodeIds = specialCarParts.stream()
                .map(CarPart::getNode)
                .toList();

        return requireNodeIds.stream().filter(x -> !nodeIds.contains(x)).toList();
    }
}
