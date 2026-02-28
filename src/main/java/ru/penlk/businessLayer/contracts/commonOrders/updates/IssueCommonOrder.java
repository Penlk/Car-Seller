package ru.penlk.businessLayer.contracts.commonOrders.updates;

import ru.penlk.businessLayer.contracts.commonOrders.models.IssueCommonOrderDto;

public abstract class IssueCommonOrder {
    private IssueCommonOrder() { }

    public record Request(long clientId, long carId) { }

    public static class Response {
        public record Failure(String Message) { }

        public record Success(IssueCommonOrderDto dto) { }
    }
}
