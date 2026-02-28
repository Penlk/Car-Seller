package ru.penlk.businessLayer.contracts.specialOrders;

import ru.penlk.businessLayer.contracts.specialOrders.models.SpecialOrderDto;
import ru.penlk.businessLayer.contracts.specialOrders.models.CreateSpecialOrderDto;

public abstract class CreateSpecialOrder {
    private CreateSpecialOrder() {}

    public record Request(CreateSpecialOrderDto carDto) {}

    public static class Response {
        public record Failure(String Message) { }

        public record Success(SpecialOrderDto dto) { }
    }
}
