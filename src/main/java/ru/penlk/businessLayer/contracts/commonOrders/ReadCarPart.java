package ru.penlk.businessLayer.contracts.commonOrders;

import ru.penlk.businessLayer.contracts.commonOrders.models.CommonOrderDto;

public abstract class ReadCarPart {
    private ReadCarPart() {}

    public record Request(long id) {}

    public static class Response {
        public record Failure(String Message) { }

        public record Success(CommonOrderDto dto) { }
    }
}
