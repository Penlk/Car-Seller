package ru.penlk.business.implementations;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.penlk.business.contracts.configurations.ConfiguratorSecurity;
import ru.penlk.dao.entities.configurations.specials.Configurator;
import ru.penlk.dao.repositories.interfaces.configurations.ConfiguratorRepository;

import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfiguratorSecurityImpl implements ConfiguratorSecurity {
    private final ConfiguratorRepository configuratorRepository;

    @Override
    public Boolean isOwner(Long configuratorId) {
        Optional<Configurator> optionalConfigurator = configuratorRepository.findById(configuratorId);

        if (optionalConfigurator.isEmpty()) {
            return false;
        }

        Configurator order = optionalConfigurator.get();

        return order.getOwnerId().equals(getUserId());
    }

    private String getUserId() {
        return Objects.requireNonNull(SecurityContextHolder.getContext()
                        .getAuthentication())
                .getName();
    }
}
