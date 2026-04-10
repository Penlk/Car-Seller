package ru.penlk.business.contracts.cars;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.penlk.business.contracts.ServiceException;
import ru.penlk.business.contracts.cars.fk.DefaultConfigurationFactory;
import ru.penlk.business.contracts.cars.fk.RequireNodeFactory;
import ru.penlk.business.contracts.cars.fk.SpecialAllowedPartFactory;
import ru.penlk.dao.entities.cars.Car;

@Service
@Transactional
public interface CarService {
    Car create(Car request,
               DefaultConfigurationFactory defaultConfigurationFactory,
               SpecialAllowedPartFactory specialAllowedPartFactory,
               RequireNodeFactory requireNodeFactory
    ) throws ServiceException;

    Car read(Long carId) throws ServiceException;

    Car update(Car request) throws ServiceException;

    void delete(Long id) throws ServiceException;
}
