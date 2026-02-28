package ru.penlk.businessLayer.contracts.carParts;

import ru.penlk.businessLayer.contracts.carParts.models.CarPartDto;
import ru.penlk.businessLayer.contracts.carParts.models.CreateCarPartDto;

public abstract class CreateCarPart {
    private CreateCarPart() {}

    public record Request(CreateCarPartDto carDto) {}

    public static class Response {
        public record Failure(String Message) { }

        public record Success(CarPartDto dto) { }
    }
}
