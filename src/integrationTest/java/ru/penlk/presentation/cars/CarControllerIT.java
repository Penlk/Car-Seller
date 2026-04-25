package ru.penlk.presentation.cars;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import ru.penlk.config.AbstractIntegrationTest;
import ru.penlk.dao.entities.cars.Car;
import ru.penlk.dao.entities.cars.CarDrive;
import ru.penlk.dao.entities.cars.Fuel;
import ru.penlk.dao.entities.cars.GearShiftBox;
import ru.penlk.dao.entities.vo.EnginePower;
import ru.penlk.dao.entities.vo.EngineVolume;
import ru.penlk.dao.entities.vo.Price;
import ru.penlk.dao.repositories.interfaces.cars.CarRepository;
import ru.penlk.presentation.cars.models.CarDto;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.greaterThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("CarController Integration Tests")
@AutoConfigureMockMvc
class CarControllerIT extends AbstractIntegrationTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CarRepository carRepository;
    private Car testCar;

    @BeforeEach
    void setUp() {
        carRepository.deleteAll();

        testCar = new Car();
        testCar.setBrand("Toyota");
        testCar.setModel("Camry");
        testCar.setBody("Sedan");
        testCar.setPrice(new Price(new BigDecimal("2500000")));
        testCar.setFuel(Fuel.PETROL);
        testCar.setEnginePower(new EnginePower(new BigDecimal("177")));
        testCar.setEngineVolume(new EngineVolume(new BigDecimal("2.5")));
        testCar.setGearShiftBox(GearShiftBox.AUTOMATIC);
        testCar.setCarDrive(CarDrive.FRONT);
        testCar.setColour("Black");
    }

    @Test
    void shouldGetCarByIdSuccessfully() throws Exception {
        Car savedCar = carRepository.save(testCar);

        mockMvc.perform(get("/cars/{id}", savedCar.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brand").value("Toyota"))
                .andExpect(jsonPath("$.model").value("Camry"))
                .andExpect(jsonPath("$.body").value("Sedan"))
                .andExpect(jsonPath("$.fuel").value("PETROL"));
    }

    @Test
    void shouldReturnNotFoundWhenCarDoesNotExist() throws Exception {
        mockMvc.perform(get("/cars/{id}", 9999L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateCarSuccessfully() throws Exception {
        String createCarJson = """
                {
                    "brand": "BMW",
                    "model": "X5",
                    "body": "SUV",
                    "price": 5000000,
                    "fuel": "DIESEL",
                    "enginePower": 340.0,
                    "engineVolume": 3.0,
                    "gearShiftBox": "AUTOMATIC",
                    "carDrive": "FULL",
                    "colour": "Silver",
                    "defaultConfigurationIds": [],
                    "specialAllowedParts": [],
                    "requireNodeIds": []
                }
                """;

        mockMvc.perform(post("/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createCarJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", greaterThan(0)))
                .andExpect(jsonPath("$.brand").value("BMW"))
                .andExpect(jsonPath("$.model").value("X5"))
                .andExpect(jsonPath("$.fuel").value("DIESEL"));
    }

    @Test
    void shouldUpdateCarSuccessfully() throws Exception {
        Car savedCar = carRepository.save(testCar);

        CarDto updateDto = new CarDto(
                savedCar.getId(),
                new BigDecimal("2300000"),
                "Honda",
                "Accord",
                "Sedan",
                Fuel.PETROL,
                new BigDecimal("192"),
                new BigDecimal("2.0"),
                GearShiftBox.AUTOMATIC,
                CarDrive.FRONT,
                "White"
        );

        mockMvc.perform(patch("/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brand").value("Honda"))
                .andExpect(jsonPath("$.model").value("Accord"))
                .andExpect(jsonPath("$.colour").value("White"));
    }

    @Test
    void shouldDeleteCarSuccessfully() throws Exception {
        Car savedCar = carRepository.save(testCar);
        Long id = savedCar.getId();

        mockMvc.perform(delete("/cars/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Verify car is deleted
        mockMvc.perform(get("/cars/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingNonExistentCar() throws Exception {
        CarDto updateDto = new CarDto(
                9999L,
                new BigDecimal("2300000"),
                "Honda",
                "Accord",
                "Sedan",
                Fuel.PETROL,
                new BigDecimal("192"),
                new BigDecimal("2.0"),
                GearShiftBox.AUTOMATIC,
                CarDrive.FRONT,
                "White"
        );

        mockMvc.perform(patch("/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldValidateRequiredFields() throws Exception {
        String invalidCarJson = """
                {
                    "brand": "BMW"
                }
                """;

        mockMvc.perform(post("/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidCarJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldPersistCarWithAllEnumValues() throws Exception {
        // Test PETROL
        testCar.setFuel(Fuel.PETROL);
        testCar.setGearShiftBox(GearShiftBox.MANUAL);
        testCar.setCarDrive(CarDrive.REAR);
        Car savedCar = carRepository.save(testCar);

        mockMvc.perform(get("/cars/{id}", savedCar.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fuel").value("PETROL"))
                .andExpect(jsonPath("$.gearShiftBox").value("MANUAL"))
                .andExpect(jsonPath("$.carDrive").value("REAR"));
    }

    @Test
    void shouldReturnCorrectContentType() throws Exception {
        Car savedCar = carRepository.save(testCar);

        mockMvc.perform(get("/cars/{id}", savedCar.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
