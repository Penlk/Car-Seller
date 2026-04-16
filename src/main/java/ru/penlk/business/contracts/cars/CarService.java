package ru.penlk.business.contracts.cars;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.penlk.business.contracts.ServiceException;
import ru.penlk.business.contracts.cars.fk.DefaultConfigurationProvider;
import ru.penlk.business.contracts.cars.fk.RequireNodeProvider;
import ru.penlk.business.contracts.cars.fk.SpecialAllowedPartProvider;
import ru.penlk.dao.entities.cars.Car;

@Service
@Transactional
public interface CarService {
    Car create(Car request,
               DefaultConfigurationProvider defaultConfigurationFactory,
               SpecialAllowedPartProvider specialAllowedPartProvider,
               RequireNodeProvider requireNodeProvider
    ) throws ServiceException;

    Car find(Long carId) throws ServiceException;

    Car update(Car request) throws ServiceException;

    void delete(Long id) throws ServiceException;
}
