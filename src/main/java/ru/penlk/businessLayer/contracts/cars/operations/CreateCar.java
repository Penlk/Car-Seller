package ru.penlk.businessLayer.contracts.cars.operations;

import ru.penlk.businessLayer.contracts.cars.models.CarDto;
import ru.penlk.businessLayer.contracts.cars.models.CreateCarDto;

public abstract class CreateCar {
    private CreateCar() {}

    public record Request(CreateCarDto carDto) {}

    public static class Response {
        public record Failure(String Message) { }

        public record Success(CarDto carDto) { }
    }
}
