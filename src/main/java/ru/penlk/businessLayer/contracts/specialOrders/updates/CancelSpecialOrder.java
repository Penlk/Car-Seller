package ru.penlk.businessLayer.contracts.specialOrders.updates;

public abstract class CancelSpecialOrder {
    private CancelSpecialOrder() { }

    public record Request(long orderId) { }

    public static class Response {
        public record Failure(String Message) { }

        public record Success() { }
    }
}
