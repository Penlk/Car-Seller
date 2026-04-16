package ru.penlk.business.implementations;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.penlk.business.contracts.ServiceException;
import ru.penlk.business.contracts.cars.CarService;
import ru.penlk.business.contracts.cars.fk.DefaultConfigurationProvider;
import ru.penlk.business.contracts.cars.fk.RequireNodeProvider;
import ru.penlk.business.contracts.cars.fk.SpecialAllowedPartProvider;
import ru.penlk.dao.entities.cars.Car;
import ru.penlk.dao.entities.configurations.defaults.DefaultConfiguration;
import ru.penlk.dao.entities.nodes.RequireNode;
import ru.penlk.dao.entities.orders.special.SpecialAllowedPart;
import ru.penlk.dao.repositories.interfaces.cars.CarPartRepository;
import ru.penlk.dao.repositories.interfaces.cars.CarRepository;
import ru.penlk.dao.repositories.interfaces.nodes.NodeRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Service
@Transactional
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final CarPartRepository carPartRepository;
    private final NodeRepository nodeRepository;

    @Override
    public Car create(Car request,
                      DefaultConfigurationProvider defaultConfigurationFactory,
                      SpecialAllowedPartProvider specialAllowedPartProvider,
                      RequireNodeProvider requireNodeProvider
    ) throws ServiceException {
        Set<DefaultConfiguration> defaultConfigurations = new HashSet<>();
        Set<SpecialAllowedPart> specialAllowedParts = new HashSet<>();
        Set<RequireNode> requireNodes = new HashSet<>();

        try {
            defaultConfigurations = defaultConfigurationFactory.getDefaultConfigurations(request, carPartRepository);
            specialAllowedParts = specialAllowedPartProvider.getSpecialAllowedParts(request, carPartRepository);
            requireNodes = requireNodeProvider.getRequireNodes(request, nodeRepository);
        } catch (IllegalArgumentException e) {
            throw new ServiceException(e.getMessage());
        }

        request.setDefaultConfiguration(defaultConfigurations);
        request.setSpecialAllowedParts(specialAllowedParts);
        request.setRequireNodes(requireNodes);

        return carRepository.save(request);
    }

    @Override
    public Car find(Long id) throws ServiceException {
        Optional<Car> carOptional = carRepository.findById(id);

        if (carOptional.isPresent()) {
            return carOptional.get();
        }

        throw new ServiceException(String.format("Car with id: {%d} not found", id));
    }

    @Override
    public Car update(Car request) throws ServiceException {
        Car car = carRepository.findById(request.getId()).orElseThrow(
                () -> new ServiceException(String.format("Car with id: {%d} not found", request.getId()))
        );

        car.setPrice(request.getPrice());

        car.setBrand(request.getBrand());

        car.setModel(request.getModel());

        car.setBody(request.getBody());

        car.setFuel(request.getFuel());

        car.setEnginePower(request.getEnginePower());

        car.setEngineVolume(request.getEngineVolume());

        car.setGearShiftBox(request.getGearShiftBox());

        car.setCarDrive(request.getCarDrive());

        car.setColour(request.getColour());

        car.setDefaultConfiguration(request.getDefaultConfiguration());

        car.setSpecialAllowedParts(request.getSpecialAllowedParts());

        car.setRequireNodes(request.getRequireNodes());

        return carRepository.save(car);
    }

    @Override
    public void delete(Long id) throws ServiceException {
        carRepository.deleteById(id);
    }
}
