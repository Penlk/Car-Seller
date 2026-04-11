package ru.penlk.business.contracts.configurations.fk;

import ru.penlk.dao.entities.configurations.specials.Configurator;
import ru.penlk.dao.entities.configurations.specials.SpecialConfiguration;

import java.util.Set;

public interface SpecialConfigurationFactory {
    Set<SpecialConfiguration> create(Configurator configurator);
}
