package ru.penlk.business.implementations.fk;

import lombok.AllArgsConstructor;
import ru.penlk.business.contracts.cars.fk.DefaultConfigurationProvider;
import ru.penlk.dao.entities.BaseEntity;
import ru.penlk.dao.entities.cars.Car;
import ru.penlk.dao.entities.cars.CarPart;
import ru.penlk.dao.entities.configurations.defaults.DefaultConfiguration;
import ru.penlk.dao.repositories.interfaces.cars.CarPartRepository;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
public class DefaultConfigurationProviderImpl implements DefaultConfigurationProvider {
    private final Set<Long> defaultConfigurationIds;

    @Override
    public Set<DefaultConfiguration> getDefaultConfigurations(Car car, CarPartRepository repository) {
        Set<CarPart> actuallyCarParts = new HashSet<>(repository.findAllById(defaultConfigurationIds));

        Set<Long> actuallyCarPartIds = new HashSet<>(actuallyCarParts.stream().map(BaseEntity::getId).toList());

        defaultConfigurationIds.forEach(id -> {
            if (!actuallyCarPartIds.contains(id)) {
                throw new IllegalArgumentException("Car part id " + id + " not found");
            }
        });

        return new HashSet<>(actuallyCarParts.stream().map(x -> new DefaultConfiguration(x, car)).toList());
    }
}
