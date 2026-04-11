package ru.penlk.business.implementations;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.penlk.business.contracts.ServiceException;
import ru.penlk.business.contracts.configurations.ConfiguratorService;
import ru.penlk.business.contracts.configurations.fk.SpecialConfigurationFactory;
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
    private final CarPartRepository carPartRepository;
    private final CarRepository carRepository;

    @Override
    public Configurator create(Configurator configurator, Long carId, SpecialConfigurationFactory specialConfigurationFactory) {
        Set<SpecialConfiguration> specialConfigurations = new HashSet<>();

        Car car = carRepository.findById(carId).orElseThrow(() -> new ServiceException("Car not found with id " + carId));

        configurator.setCar(car);

        try {
            specialConfigurations = specialConfigurationFactory.create(configurator);
        } catch (IllegalArgumentException e) {
            throw new ServiceException(e.getMessage());
        }

        configurator.setSpecialConfigurations(specialConfigurations);
        configurator.setFinished(false);

        return configuratorRepository.save(configurator);
    }

    @Override
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
    public Configurator read(Long id) {

        return configuratorRepository.findById(id)
                .orElseThrow(() -> new ServiceException("Cannot find configurator with id: " + id));
    }

    @Override
    public void delete(Long id) {
        Configurator configurator = configuratorRepository.findById(id)
                .orElseThrow(() -> new ServiceException("Cannot find configurator with id: " + id));

        if (configurator.isFinished()) {
            throw new ServiceException("Cannot remove finished configurator");
        }

        configuratorRepository.delete(configurator);
    }
}
