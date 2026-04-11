package ru.penlk.business.contracts.configurations;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.penlk.business.contracts.configurations.fk.SpecialConfigurationFactory;
import ru.penlk.dao.entities.configurations.specials.Configurator;

@Service
@Transactional
public interface ConfiguratorService {
    Configurator create(Configurator configurator, Long carId, SpecialConfigurationFactory specialConfigurationFactory);

    Configurator addPart(Long configuratorId, Long carPartId);

    Configurator removePart(Long configuratorId, Long carPartId);

    Configurator read(Long id);

    void delete(Long id);
}
