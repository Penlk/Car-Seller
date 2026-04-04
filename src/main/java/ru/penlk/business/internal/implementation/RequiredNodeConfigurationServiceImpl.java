package ru.penlk.business.internal.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.penlk.business.contracts.DomainValidationException;
import ru.penlk.business.internal.RequiredNodeConfigurationService;
import ru.penlk.dao.entities.cars.CarPart;
import ru.penlk.dao.entities.cars.Car;
import ru.penlk.dao.entities.configurations.defaults.DefaultConfiguration;
import ru.penlk.dao.entities.nodes.RequireNode;
import ru.penlk.dao.repositories.interfaces.cars.CarPartRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class RequiredNodeConfigurationServiceImpl implements RequiredNodeConfigurationService {
    @Override
    public Collection<CarPart> completeRequireNodes(Car car, Collection<CarPart> specialCarParts) {
        List<Long> fillRequiredNodes = specialCarParts.stream().map(x -> x.getNode().getId()).toList();

        List<RequireNode> missingNodeIds = car.getRequireNodes().stream()
                .filter(x -> fillRequiredNodes.contains(x.getNode().getId()))
                .toList();

        return tryToCompleteRequireNodes(car, missingNodeIds.stream().map(x -> x.getNode().getId()).toList());
    }

    private List<CarPart> tryToCompleteRequireNodes(Car car, List<Long> missingNodeIds) {
        Collection<DefaultConfiguration> defaultConfiguration = car.getDefaultConfiguration();

        List<Long> copyMissingNodeIds = new ArrayList<>(missingNodeIds.stream().toList());

        List<CarPart> completedCarParts = defaultConfiguration.stream()
                .filter(x -> copyMissingNodeIds.contains(x.getCarPart().getNode().getId()))
                .peek(x -> copyMissingNodeIds.remove(x.getCarPart().getNode().getId()))
                .map(DefaultConfiguration::getCarPart)
                .toList();

        if (!copyMissingNodeIds.isEmpty()) {
            throw new DomainValidationException("Missing required node ids: " + copyMissingNodeIds);
        }

        return completedCarParts;
    }
}
