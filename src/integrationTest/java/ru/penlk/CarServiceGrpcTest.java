package ru.penlk;

import io.grpc.Server;
import io.grpc.stub.StreamObserver;
import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.data.jpa.autoconfigure.DataJpaRepositoriesAutoConfiguration;
import org.springframework.boot.hibernate.autoconfigure.HibernateJpaAutoConfiguration;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;
import org.springframework.boot.kafka.autoconfigure.KafkaAutoConfiguration;
import org.springframework.boot.liquibase.autoconfigure.LiquibaseAutoConfiguration;
import org.springframework.boot.security.autoconfigure.SecurityAutoConfiguration;
import org.springframework.boot.security.autoconfigure.web.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.SimpleTransactionStatus;
import ru.penlk.business.contracts.ServiceException;
import ru.penlk.business.implementations.CarStorageServiceImpl;
import ru.penlk.dao.entities.cars.Car;
import ru.penlk.dao.repositories.interfaces.cars.CarRepository;
import storage.CarServiceGrpc;
import storage.Cars;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest(
        classes = CarServiceGrpcTest.TestConfig.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE
)
class CarServiceGrpcTest {

    private static final int GRPC_PORT = freePort();
    private static final Server GRPC_SERVER = startGrpcServer(GRPC_PORT);

    @DynamicPropertySource
    static void grpcProps(DynamicPropertyRegistry registry) {
        registry.add("grpc.client.storage-service.address", () -> "static://localhost:" + GRPC_PORT);
        registry.add("grpc.client.storage-service.negotiationType", () -> "PLAINTEXT");
    }

    @SpringBootConfiguration
    @EnableAutoConfiguration(exclude = {
            DataSourceAutoConfiguration.class,
            HibernateJpaAutoConfiguration.class,
            LiquibaseAutoConfiguration.class,
            DataJpaRepositoriesAutoConfiguration.class,
            KafkaAutoConfiguration.class,
            SecurityAutoConfiguration.class,
            SecurityFilterAutoConfiguration.class
    })
    @Import(CarStorageServiceImpl.class)
    static class TestConfig {

        @Bean
        CarRepository carRepository() {
            return Mockito.mock(CarRepository.class);
        }

        @Bean
        @Primary
        PlatformTransactionManager transactionManager() {
            return new PlatformTransactionManager() {
                @Override
                public TransactionStatus getTransaction(TransactionDefinition definition) {
                    return new SimpleTransactionStatus();
                }

                @Override
                public void commit(TransactionStatus status) {
                }

                @Override
                public void rollback(TransactionStatus status) {
                }
            };
        }
    }

    @Autowired
    private CarStorageServiceImpl service;

    @Autowired
    private CarRepository carRepository;

    @BeforeEach
    void setUp() {
        Mockito.reset(carRepository);
    }

    @AfterAll
    static void shutdown() {
        if (GRPC_SERVER != null) {
            GRPC_SERVER.shutdownNow();
        }
    }

    @Test
    void getAllAvailableCars_shouldReturnCars() {
        Car car1 = car(1L);
        Car car2 = car(2L);

        when(carRepository.findAllById(List.of(1L, 2L))).thenReturn(List.of(car1, car2));

        Set<Car> result = service.getAllAvailableCars();

        assertEquals(Set.of(1L, 2L), result.stream().map(Car::getId).collect(Collectors.toSet()));
    }

    @Test
    void getById_shouldReturnCar_whenAvailable() {
        Car car = car(42L);
        when(carRepository.getReferenceById(42L)).thenReturn(car);

        Car result = service.getById(42L);

        assertNotNull(result);
        assertEquals(42L, result.getId());
    }

    @Test
    void getById_shouldThrow_whenCarNotAvailable() {
        ServiceException exception = assertThrows(
                ServiceException.class,
                () -> service.getById(999L)
        );

        assertEquals("Car is not available", exception.getMessage());
    }

    private static Car car(long id) {
        Car car = new Car();
        car.setId(id);
        return car;
    }

    private static int freePort() {
        try (ServerSocket socket = new ServerSocket(0)) {
            return socket.getLocalPort();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Server startGrpcServer(int port) {
        try {
            return NettyServerBuilder
                    .forPort(port)
                    .addService(new FakeCarService())
                    .build()
                    .start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static class FakeCarService extends CarServiceGrpc.CarServiceImplBase {

        @Override
        public void getAvailableCars(
                Cars.GetAvailableCarsRequest request,
                StreamObserver<Cars.GetAvailableCarsResponse> responseObserver
        ) {
            Cars.GetAvailableCarsResponse response = Cars.GetAvailableCarsResponse.newBuilder()
                    .addCars(Cars.Car.newBuilder().setId(1L).setFree(5L).build())
                    .addCars(Cars.Car.newBuilder().setId(2L).setFree(3L).build())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }

        @Override
        public void getAvailableCar(
                Cars.GetAvailableCarRequest request,
                StreamObserver<Cars.GetAvailableCarResponse> responseObserver
        ) {
            if (request.getCarId() == 42L) {
                Cars.GetAvailableCarResponse response = Cars.GetAvailableCarResponse.newBuilder()
                        .setCar(Cars.Car.newBuilder().setId(42L).setFree(7L).build())
                        .build();

                responseObserver.onNext(response);
            } else {
                responseObserver.onNext(Cars.GetAvailableCarResponse.newBuilder().build());
            }

            responseObserver.onCompleted();
        }
    }
}