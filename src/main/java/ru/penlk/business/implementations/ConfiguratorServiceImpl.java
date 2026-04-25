package ru.penlk.business.implementations;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.penlk.business.contracts.ServiceException;
import ru.penlk.business.contracts.configurations.ConfiguratorService;
import ru.penlk.business.contracts.configurations.fk.SpecialConfigurationProvider;
import ru.penlk.dao.entities.cars.Car;
import ru.penlk.dao.entities.configurations.specials.Configurator;
import ru.penlk.dao.entities.configurations.specials.SpecialConfiguration;
import ru.penlk.dao.entities.orders.special.SpecialAllowedPart;
import ru.penlk.dao.repositories.interfaces.cars.CarPartRepository;
import ru.penlk.dao.repositories.interfaces.cars.CarRepository;
import ru.penlk.dao.repositories.interfaces.configurations.ConfiguratorRepository;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Service
@Transactional
public class ConfiguratorServiceImpl implements ConfiguratorService {
    private final ConfiguratorRepository configuratorRepository;
    private final CarRepository carRepository;

    @Override
    @PreAuthorize("hasRole('USER')")
    public Configurator create(Configurator configurator, Long carId, SpecialConfigurationProvider specialConfigurationProvider) {
        Set<SpecialConfiguration> specialConfigurations = new HashSet<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null) {
            throw new ServiceException("Authentication required");
        }

        String userId = auth.getName();

        Car car = carRepository.findById(carId).orElseThrow(() -> new ServiceException("Car not found with id " + carId));

        configurator.setCar(car);

        try {
            specialConfigurations = specialConfigurationProvider.create(configurator);
        } catch (IllegalArgumentException e) {
            throw new ServiceException(e.getMessage());
        }

        configurator.setSpecialConfigurations(specialConfigurations);
        configurator.setFinished(false);
        configurator.setOwnerId(userId);

        return configuratorRepository.save(configurator);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') and @configuratorSecurityImpl.isOwner(#configuratorId)")
    public Configurator addPart(Long configuratorId, Long carPartId) {
        Configurator configurator = configuratorRepository.findById(configuratorId)
                .orElseThrow(() -> new ServiceException("Cannot find configurator with id: " + configuratorId));

        if (configurator.isFinished()) {
            throw new ServiceException("Cannot add part with finished configurator");
        }

        SpecialAllowedPart specialAllowedPart =
                configurator.getCar().getSpecialAllowedParts().stream().filter(x -> x.getId().equals(carPartId)).findFirst().orElseThrow(() ->
                        new ServiceException("Cannot find car's part with id: " + carPartId)
                );

        SpecialConfiguration specialConfiguration = new SpecialConfiguration(configurator, specialAllowedPart);

        configurator.getSpecialConfigurations().add(specialConfiguration);

        return configuratorRepository.save(configurator);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') and @configuratorSecurityImpl.isOwner(#configuratorId)")
    public Configurator removePart(Long configuratorId, Long carPartId) {
        Configurator configurator = configuratorRepository.findById(configuratorId)
                .orElseThrow(() -> new ServiceException("Cannot find configurator with id: " + configuratorId));

        if (configurator.isFinished()) {
            throw new ServiceException("Cannot remove part with finished configurator");
        }

        configurator.getSpecialConfigurations().removeIf(x -> x.getSpecialAllowedPart().getId().equals(carPartId));

        return configuratorRepository.save(configurator);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') and @configuratorSecurityImpl.isOwner(#configuratorId)")
    public Configurator find(Long configuratorId) {

        return configuratorRepository.findById(configuratorId)
                .orElseThrow(() -> new ServiceException("Cannot find configurator with id: " + configuratorId));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') and @configuratorSecurityImpl.isOwner(#configuratorId)")
    public void delete(Long configuratorId) {
        Configurator configurator = configuratorRepository.findById(configuratorId)
                .orElseThrow(() -> new ServiceException("Cannot find configurator with id: " + configuratorId));

        if (configurator.isFinished()) {
            throw new ServiceException("Cannot remove finished configurator");
        }

        configuratorRepository.delete(configurator);
    }
}
