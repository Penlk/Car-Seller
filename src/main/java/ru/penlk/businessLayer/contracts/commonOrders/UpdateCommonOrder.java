package ru.penlk.businessLayer.contracts.commonOrders;

import ru.penlk.businessLayer.contracts.commonOrders.models.CommonOrderDto;

public abstract class UpdateCommonOrder {
    private UpdateCommonOrder() {}

    public record Request(CommonOrderDto carDto) {}

    public static class Response {
        public record Failure(String Message) { }

        public record Success(CommonOrderDto dto) { }
    }
}
