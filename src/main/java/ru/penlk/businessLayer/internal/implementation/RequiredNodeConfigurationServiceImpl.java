package ru.penlk.businessLayer.internal.implementation;

import lombok.AllArgsConstructor;
import ru.penlk.businessLayer.contracts.DomainValidationException;
import ru.penlk.businessLayer.contracts.ServiceException;
import ru.penlk.businessLayer.internal.RequiredNodeConfigurationService;
import ru.penlk.dataAcessLayer.entities.carParts.CarPart;
import ru.penlk.dataAcessLayer.entities.carParts.CarPartId;
import ru.penlk.dataAcessLayer.entities.cars.Car;
import ru.penlk.dataAcessLayer.entities.nodes.NodeId;
import ru.penlk.dataAcessLayer.repositories.interfaces.carParts.CarPartRepository;
import ru.penlk.dataAcessLayer.repositories.interfaces.orders.commonConfigurations.CommonConfigurationRepository;
import ru.penlk.dataAcessLayer.repositories.interfaces.requireNodes.RequireNodeRepository;

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
                .filter(x -> copyMissingNodeIds.contains(x.getNodeId()))
                .peek(x -> copyMissingNodeIds.remove(x.getNodeId()))
                .toList();

        if (!copyMissingNodeIds.isEmpty()) {
            throw new DomainValidationException("Missing required node ids: " + copyMissingNodeIds);
        }

        return completedCarParts;
    }

    private List<NodeId> getRequireNodeIds(Car car, Collection<CarPart> specialCarParts) {
        Collection<NodeId> requireNodeIds = requireNodeRepository.findByCarId(car.getId());

        List<NodeId> nodeIds = specialCarParts.stream()
                .map(CarPart::getNodeId)
                .toList();

        return requireNodeIds.stream().filter(x -> !nodeIds.contains(x)).toList();
    }
}
