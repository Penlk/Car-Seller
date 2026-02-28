package ru.penlk.businessLayer.contracts.cars.operations;

import ru.penlk.businessLayer.contracts.cars.models.CarDto;

public abstract class ReadCar {
    private ReadCar() {}

    public record Request(long id) {}

    public static class Response {
        public record Failure(String Message) { }

        public record Success(CarDto dto) { }
    }
}
