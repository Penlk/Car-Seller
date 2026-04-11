package ru.penlk.business.implementations.fk;

import lombok.AllArgsConstructor;
import ru.penlk.business.contracts.configurations.fk.SpecialConfigurationProvider;
import ru.penlk.dao.entities.configurations.specials.Configurator;
import ru.penlk.dao.entities.configurations.specials.SpecialConfiguration;
import ru.penlk.dao.entities.orders.special.SpecialAllowedPart;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
public class SpecialConfigurationProviderImpl implements SpecialConfigurationProvider {
    private final Set<Long> carPartIds;

    @Override
    public Set<SpecialConfiguration> create(Configurator configurator) {
        Set<SpecialAllowedPart> actuallyAllowedParts = configurator.getCar().getSpecialAllowedParts();

        Set<Long> actuallyAllowedPartIds = new HashSet<>(actuallyAllowedParts.stream().map(SpecialAllowedPart::getId).toList());

        carPartIds.forEach(x -> {
            if (!actuallyAllowedPartIds.contains(x)) {
                throw new IllegalArgumentException("Car part id " + x + " is not allowed");
            }
        });

        return new HashSet<>(actuallyAllowedParts.stream().map(x -> new SpecialConfiguration(configurator, x)).toList());
    }
}
