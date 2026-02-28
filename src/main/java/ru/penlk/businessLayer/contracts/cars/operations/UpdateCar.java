package ru.penlk.businessLayer.contracts.cars.operations;

import ru.penlk.businessLayer.contracts.cars.models.CarDto;

public abstract class UpdateCar {
    private UpdateCar() {}

    public record Request(CarDto carDto) {}

    public static class Response {
        public record Failure(String Message) { }

        public record Success(CarDto carDto) { }
    }
}
