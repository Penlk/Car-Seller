package ru.penlk;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import penlk.Main;
import penlk.dao.entities.CarStock;
import penlk.dao.repositories.CarStockRepository;
import storage.CarServiceGrpc;
import storage.Cars;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@SpringBootTest(
        classes = Main.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE,
        properties = {
                "grpc.server.port=9091",
                "spring.liquibase.enabled=true"
        }
)
class CarServiceGrpcTest {

    @Container
    @ServiceConnection
    static final PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:16-alpine")
                    .withDatabaseName("testdb")
                    .withUsername("test")
                    .withPassword("test");

    @Autowired
    private CarStockRepository carStockRepository;

    private ManagedChannel channel;

    private CarServiceGrpc.CarServiceBlockingStub stub() {
        if (channel == null || channel.isShutdown() || channel.isTerminated()) {
            channel = ManagedChannelBuilder
                    .forAddress("localhost", 9091)
                    .usePlaintext()
                    .build();
        }
        return CarServiceGrpc.newBlockingStub(channel);
    }

    @AfterEach
    void tearDown() {
        if (channel != null) {
            channel.shutdownNow();
        }
        carStockRepository.deleteAll();
    }

    @Test
    void shouldReturnAvailableCars() {
        CarStock first = new CarStock();
        first.setCarSourceId(1L);
        first.setStock(10L);
        first.setReserved(2L);

        CarStock second = new CarStock();
        second.setCarSourceId(2L);
        second.setStock(5L);
        second.setReserved(5L);

        carStockRepository.save(first);
        carStockRepository.save(second);

        Cars.GetAvailableCarsResponse response = stub().getAvailableCars(
                Cars.GetAvailableCarsRequest.newBuilder().build()
        );

        assertEquals(1, response.getCarsCount());
        assertEquals(1L, response.getCars(0).getId());
        assertEquals(8L, response.getCars(0).getFree());
    }

    @Test
    void shouldReturnSingleAvailableCar() {
        CarStock stock = new CarStock();
        stock.setCarSourceId(10L);
        stock.setStock(20L);
        stock.setReserved(3L);

        carStockRepository.save(stock);

        Cars.GetAvailableCarResponse response = stub().getAvailableCar(
                Cars.GetAvailableCarRequest.newBuilder()
                        .setCarId(10L)
                        .build()
        );

        assertTrue(response.hasCar());
        assertEquals(10L, response.getCar().getId());
        assertEquals(17L, response.getCar().getFree());
    }

    @Test
    void shouldReturnEmptyWhenCarNotFound() {
        Cars.GetAvailableCarResponse response = stub().getAvailableCar(
                Cars.GetAvailableCarRequest.newBuilder()
                        .setCarId(999L)
                        .build()
        );

        assertFalse(response.hasCar());
    }

    @Test
    void shouldReturnEmptyCarsList() {
        Cars.GetAvailableCarsResponse response = stub().getAvailableCars(
                Cars.GetAvailableCarsRequest.newBuilder().build()
        );

        assertTrue(response.getCarsList().isEmpty());
    }
}