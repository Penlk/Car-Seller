package ru.penlk.business.implementations;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.penlk.business.contracts.ServiceException;
import ru.penlk.business.contracts.cars.CarStorageService;
import ru.penlk.dao.entities.cars.Car;
import ru.penlk.dao.repositories.interfaces.cars.CarRepository;
import storage.CarServiceGrpc;
import storage.Cars;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class CarStorageServiceImpl implements CarStorageService {
    private final CarRepository carRepository;

    @GrpcClient("storage-service")
    private CarServiceGrpc.CarServiceBlockingStub stub;

    public CarStorageServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public Set<Car> getAllAvailableCars() {
        List<Long> carIds = stub.getAvailableCars(Cars.GetAvailableCarsRequest.newBuilder().build()).getCarsList()
                .stream().map(Cars.Car::getId).toList();

        return new HashSet<>(carRepository.findAllById(carIds));
    }

    @Override
    public Car getById(Long carId) {
        Cars.GetAvailableCarResponse car = stub.getAvailableCar(Cars.GetAvailableCarRequest.newBuilder().build());

        if (!car.hasCar()) {
            throw new ServiceException("Car is not available");
        }

        return carRepository.getReferenceById(carId);
    }
}
