package ru.penlk.businessLayer.contracts.specialOrders;

import ru.penlk.businessLayer.contracts.specialOrders.models.SpecialOrderDto;

public abstract class ReadSpecialOrder {
    private ReadSpecialOrder() {}

    public record Request(long id) {}

    public static class Response {
        public record Failure(String Message) { }

        public record Success(SpecialOrderDto dto) { }
    }
}
