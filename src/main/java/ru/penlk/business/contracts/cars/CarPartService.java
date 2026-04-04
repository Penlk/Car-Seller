package ru.penlk.business.contracts.cars;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.penlk.business.contracts.ServiceException;
import ru.penlk.dao.entities.cars.CarPart;

@Service
@Transactional
public interface CarPartService {
    CarPart create(CarPart request);

    CarPart read(Long carPartId) throws ServiceException;

    CarPart update(CarPart request) throws ServiceException;

    void delete(Long carPartId) throws ServiceException;
}
