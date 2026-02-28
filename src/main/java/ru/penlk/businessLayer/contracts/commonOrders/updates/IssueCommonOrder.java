package ru.penlk.businessLayer.contracts.commonOrders.updates;

import ru.penlk.businessLayer.contracts.commonOrders.models.IssueCommonOrderDto;

import java.util.Collection;

public abstract class IssueCommonOrder {
    private IssueCommonOrder() { }

    public record Request(long clientId, long carId, Collection<Long> carPartIds) { }

    public static class Response {
        public record Failure(String Message) { }

        public record Success(IssueCommonOrderDto dto) { }
    }
}
