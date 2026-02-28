package ru.penlk.businessLayer.contracts.commonOrders;

public abstract class DeleteCarPart {
    private DeleteCarPart() {}

    public record Request(long id) {}

    public static class Response {
        public record Failure(String Message) { }

        public record Success() { }
    }
}
