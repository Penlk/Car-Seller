package ru.penlk.businessLayer.contracts.specialOrders;

public abstract class DeleteSpecialOrder {
    private DeleteSpecialOrder() {}

    public record Request(long id) {}

    public static class Response {
        public record Failure(String Message) { }

        public record Success() { }
    }
}
