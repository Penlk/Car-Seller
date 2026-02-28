package ru.penlk.businessLayer.contracts.specialOrders.updates;

import ru.penlk.businessLayer.contracts.specialOrders.models.SpecialOrderDto;

public abstract class CompletedSpecialOrder {
    private CompletedSpecialOrder() { }

    public record Request(long orderId) { }

    public static class Response {
        public record Failure(String Message) { }

        public record Success(SpecialOrderDto dto) { }
    }
}
