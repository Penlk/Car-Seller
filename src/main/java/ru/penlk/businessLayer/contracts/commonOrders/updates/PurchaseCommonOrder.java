package ru.penlk.businessLayer.contracts.commonOrders.updates;

import ru.penlk.businessLayer.contracts.commonOrders.models.CommonOrderDto;

public abstract class PurchaseCommonOrder {
    private PurchaseCommonOrder() { }

    public record Request(long orderId) { }

    public static class Response {
        public record Failure(String Message) { }

        public record Success(CommonOrderDto dto) { }
    }
}
