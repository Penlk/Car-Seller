package ru.penlk.dao.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.penlk.dao.entities.cars.Car;
import ru.penlk.dao.entities.cars.CarDrive;
import ru.penlk.dao.entities.cars.Fuel;
import ru.penlk.dao.entities.cars.GearShiftBox;
import ru.penlk.dao.entities.vo.EnginePower;
import ru.penlk.dao.entities.vo.EngineVolume;
import ru.penlk.dao.entities.vo.Price;
import ru.penlk.dao.repositories.interfaces.cars.CarRepository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CarRepository Integration Tests")
@DataJpaTest
@Testcontainers
class CarRepositoryIT {
    @Container
    @ServiceConnection
    static final PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:16-alpine")
                    .withDatabaseName("testdb")
                    .withUsername("test")
                    .withPassword("test");
    @Autowired
    private CarRepository carRepository;

    @PersistenceContext
    private EntityManager entityManager;


    private Car testCar;

    @BeforeEach
    void setUp() {
        testCar = createTestCar("Toyota", "Camry", "Sedan", Fuel.PETROL, 177.0, "Black");
    }

    private Car createTestCar(String brand, String model, String body, Fuel fuel, Double enginePower, String colour) {
        Car car = new Car();
        car.setBrand(brand);
        car.setModel(model);
        car.setBody(body);
        car.setPrice(new Price(new BigDecimal("2500000")));
        car.setFuel(fuel);
        car.setEnginePower(new EnginePower(new BigDecimal(enginePower)));
        car.setEngineVolume(new EngineVolume(new BigDecimal("2.5")));
        car.setGearShiftBox(GearShiftBox.AUTOMATIC);
        car.setCarDrive(CarDrive.FRONT);
        car.setColour(colour);
        return car;
    }

    @Test
    @DisplayName("Should save car and generate ID")
    void shouldSaveCarToDatabaseSuccessfully() {
        Car savedCar = carRepository.save(testCar);
        
        assertNotNull(savedCar.getId());
        assertEquals("Toyota", savedCar.getBrand());
        assertEquals("Camry", savedCar.getModel());
        assertNotNull(savedCar.getCreatedAt());
    }

    @Test
    @DisplayName("Should find car by ID")
    void shouldFindCarById() {
        Car savedCar = carRepository.save(testCar);
        Long id = savedCar.getId();

        Optional<Car> foundCar = carRepository.findById(id);

        assertTrue(foundCar.isPresent());
        assertEquals("Toyota", foundCar.get().getBrand());
        assertEquals("Camry", foundCar.get().getModel());
        assertEquals("Sedan", foundCar.get().getBody());
        assertEquals(Fuel.PETROL, foundCar.get().getFuel());
    }

    @Test
    @DisplayName("Should update car successfully")
    void shouldUpdateCar() {
        Car savedCar = carRepository.save(testCar);
        Long id = savedCar.getId();

        Car carToUpdate = carRepository.findById(id).orElseThrow();
        carToUpdate.setColour("White");
        carToUpdate.setBrand("Honda");

        Car updatedCar = carRepository.save(carToUpdate);

        assertEquals("White", updatedCar.getColour());
        assertEquals("Honda", updatedCar.getBrand());
        assertEquals(id, updatedCar.getId());
    }

    @Test
    @DisplayName("Should delete car by ID")
    void shouldDeleteCar() {
        Car savedCar = carRepository.save(testCar);
        Long id = savedCar.getId();

        carRepository.deleteById(id);
        carRepository.flush();
        entityManager.clear();

        Optional<Car> deletedCar = carRepository.findById(id);
        assertFalse(deletedCar.isPresent());
    }

    @Test
    @DisplayName("Should return empty Optional when car not found")
    void shouldReturnEmptyWhenCarNotFound() {
        Optional<Car> notFoundCar = carRepository.findById(9999L);
        assertFalse(notFoundCar.isPresent());
    }

    @Test
    @DisplayName("Should find all cars")
    void shouldFindAllCars() {
        Car car1 = createTestCar("Toyota", "Camry", "Sedan", Fuel.PETROL, 177.0, "Black");
        Car car2 = createTestCar("BMW", "X5", "SUV", Fuel.DIESEL, 340.0, "Silver");

        carRepository.save(car1);
        carRepository.save(car2);

        List<Car> allCars = carRepository.findAll();
        assertTrue(allCars.size() >= 2);
    }

    @Test
    @DisplayName("Should persist all car fields correctly")
    void shouldPersistAllCarFields() {
        Car savedCar = carRepository.save(testCar);
        Optional<Car> foundCar = carRepository.findById(savedCar.getId());

        assertTrue(foundCar.isPresent());
        Car car = foundCar.get();
        
        assertEquals("Toyota", car.getBrand());
        assertEquals("Camry", car.getModel());
        assertEquals("Sedan", car.getBody());
        assertEquals(Fuel.PETROL, car.getFuel());
        assertEquals(0, BigDecimal.valueOf(177.00).compareTo(car.getEnginePower().engine_power()));
        assertEquals(0, BigDecimal.valueOf(2.5).compareTo(car.getEngineVolume().engine_volume()));
        assertEquals(GearShiftBox.AUTOMATIC, car.getGearShiftBox());
        assertEquals(CarDrive.FRONT, car.getCarDrive());
        assertEquals("Black", car.getColour());
        assertNotNull(car.getId());
        assertNotNull(car.getCreatedAt());
    }

    @Test
    @DisplayName("Should support multiple fuel types")
    void shouldSupportMultipleCarFuelTypes() {
        Car petrolCar = createTestCar("Toyota", "Camry", "Sedan", Fuel.PETROL, 177.0, "Black");
        Car dieselCar = createTestCar("Mercedes", "E-Class", "Sedan", Fuel.DIESEL, 225.0, "Gray");

        carRepository.save(petrolCar);
        carRepository.save(dieselCar);

        List<Car> allCars = carRepository.findAll();
        assertTrue(allCars.stream().anyMatch(car -> car.getFuel() == Fuel.PETROL));
        assertTrue(allCars.stream().anyMatch(car -> car.getFuel() == Fuel.DIESEL));
    }

    @Test
    @DisplayName("Should update timestamp on modification")
    void shouldUpdateTimestampOnModification() throws InterruptedException {
        Car savedCar = carRepository.save(testCar);
        Instant createdAtFirst = savedCar.getUpdatedAt();

        Thread.sleep(100);

        Car carToUpdate = carRepository.findById(savedCar.getId()).orElseThrow();
        carToUpdate.setColour("Red");
        carRepository.saveAndFlush(carToUpdate);

        Car refreshedCar = carRepository.findById(savedCar.getId()).orElseThrow();
        assertTrue(refreshedCar.getUpdatedAt().isAfter(createdAtFirst));
    }

    @Test
    @DisplayName("Should support all driving types")
    void shouldSupportAllDrivingTypes() {
        Car frontCar = createTestCar("Toyota", "Corolla", "Sedan", Fuel.PETROL, 140.0, "Blue");
        frontCar.setCarDrive(CarDrive.FRONT);
        
        Car rearCar = createTestCar("BMW", "3-Series", "Sedan", Fuel.DIESEL, 200.0, "Green");
        rearCar.setCarDrive(CarDrive.REAR);
        
        Car fullCar = createTestCar("Audi", "Q7", "SUV", Fuel.DIESEL, 340.0, "White");
        fullCar.setCarDrive(CarDrive.FULL);

        carRepository.save(frontCar);
        carRepository.save(rearCar);
        carRepository.save(fullCar);

        List<Car> allCars = carRepository.findAll();
        assertTrue(allCars.stream().anyMatch(car -> car.getCarDrive() == CarDrive.FRONT));
        assertTrue(allCars.stream().anyMatch(car -> car.getCarDrive() == CarDrive.REAR));
        assertTrue(allCars.stream().anyMatch(car -> car.getCarDrive() == CarDrive.FULL));
    }
}
