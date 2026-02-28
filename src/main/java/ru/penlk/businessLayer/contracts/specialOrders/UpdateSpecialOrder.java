package ru.penlk.businessLayer.contracts.specialOrders;

import ru.penlk.businessLayer.contracts.specialOrders.models.SpecialOrderDto;

public abstract class UpdateSpecialOrder {
    private UpdateSpecialOrder() {}

    public record Request(SpecialOrderDto carDto) {}

    public static class Response {
        public record Failure(String Message) { }

        public record Success(SpecialOrderDto dto) { }
    }
}
