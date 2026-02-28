package ru.penlk.businessLayer.contracts.specialOrders.updates;

import ru.penlk.businessLayer.contracts.specialOrders.models.IssueSpecialOrderDto;

import java.util.Collection;

public abstract class IssueSpecialOrder {
    private IssueSpecialOrder() { }

    public record Request(long clientId, long carId, Collection<Long> carPartIds) { }

    public static class Response {
        public record Failure(String Message) { }

        public record Success(IssueSpecialOrderDto dto) { }
    }
}
