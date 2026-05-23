package penlk.business.implementations;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import penlk.dao.entities.CarStock;
import penlk.dao.repositories.CarStockRepository;
import storage.CarServiceGrpc;
import storage.Cars;

import java.util.List;
import java.util.Optional;

@GrpcService
public class CarGrpcService extends CarServiceGrpc.CarServiceImplBase {
    private final CarStockRepository carStockRepository;

    public CarGrpcService(CarStockRepository carStockRepository) {
        this.carStockRepository = carStockRepository;
    }

    @Override
    public void getAvailableCars(
            Cars.GetAvailableCarsRequest request, StreamObserver<Cars.GetAvailableCarsResponse> responseObserver) {

        List<Cars.Car> cars = carStockRepository.findAvailableCars()
                .stream()
                .map(x -> Cars.Car.newBuilder().setId(x.getCarSourceId()).setFree(x.getStock() - x.getReserved()).build())
                .toList();

        Cars.GetAvailableCarsResponse response = Cars.GetAvailableCarsResponse.newBuilder().addAllCars(cars).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getAvailableCar(
            Cars.GetAvailableCarRequest request, StreamObserver<Cars.GetAvailableCarResponse> responseObserver) {

        Optional<CarStock> optionalCarStock = carStockRepository.findByCarSourceId(request.getCarId());

        Cars.GetAvailableCarResponse.Builder response = Cars.GetAvailableCarResponse.newBuilder();
        optionalCarStock.ifPresent(carStock -> response.setCar(
                Cars.Car.newBuilder()
                        .setId(carStock.getCarSourceId())
                        .setFree(carStock.getStock() - carStock.getReserved())
                        .build()
        ).build());


        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }
}
