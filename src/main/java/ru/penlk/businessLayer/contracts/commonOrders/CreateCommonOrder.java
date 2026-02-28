package ru.penlk.businessLayer.contracts.commonOrders;

import ru.penlk.businessLayer.contracts.commonOrders.models.CommonOrderDto;
import ru.penlk.businessLayer.contracts.commonOrders.models.CreateCommonOrderDto;

public abstract class CreateCommonOrder {
    private CreateCommonOrder() {}

    public record Request(CreateCommonOrderDto carDto) {}

    public static class Response {
        public record Failure(String Message) { }

        public record Success(CommonOrderDto dto) { }
    }
}
