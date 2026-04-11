package ru.penlk.business.contracts.cars;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.penlk.business.contracts.ServiceException;
import ru.penlk.dao.entities.cars.CarPart;

@Service
@Transactional
public interface CarPartService {
    CarPart create(CarPart request, Long nodeId);

    CarPart find(Long carPartId) throws ServiceException;

    CarPart update(CarPart request, Long nodeId) throws ServiceException;

    void delete(Long carPartId) throws ServiceException;
}
