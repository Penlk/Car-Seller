package ru.penlk.business.contracts.configurations;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.penlk.business.contracts.configurations.fk.SpecialConfigurationProvider;
import ru.penlk.dao.entities.configurations.specials.Configurator;

@Service
@Transactional
public interface ConfiguratorService {
    Configurator create(Configurator configurator, Long carId, SpecialConfigurationProvider specialConfigurationProvider);

    Configurator addPart(Long configuratorId, Long carPartId);

    Configurator removePart(Long configuratorId, Long carPartId);

    Configurator find(Long id);

    void delete(Long id);
}
