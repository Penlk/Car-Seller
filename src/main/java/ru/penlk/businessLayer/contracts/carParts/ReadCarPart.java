package ru.penlk.businessLayer.contracts.carParts;

import ru.penlk.businessLayer.contracts.carParts.models.CarPartDto;

public abstract class ReadCarPart {
    private ReadCarPart() {}

    public record Request(long id) {}

    public static class Response {
        public record Failure(String Message) { }

        public record Success(CarPartDto dto) { }
    }
}
