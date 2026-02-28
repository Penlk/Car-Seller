package ru.penlk.businessLayer.contracts.carParts;

import ru.penlk.businessLayer.contracts.carParts.models.CarPartDto;

public abstract class UpdateCarPart {
    private UpdateCarPart() {}

    public record Request(CarPartDto carDto) {}

    public static class Response {
        public record Failure(String Message) { }

        public record Success(CarPartDto dto) { }
    }
}
