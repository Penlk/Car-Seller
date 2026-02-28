package ru.penlk.businessLayer.contracts.commonOrders.updates;

public abstract class CancelCommonOrder {
    private CancelCommonOrder() { }

    public record Request(long orderId) { }

    public static class Response {
        public record Failure(String Message) { }

        public record Success() { }
    }
}
