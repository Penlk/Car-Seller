package ru.penlk.dataAcessLayer.repositories.interfaces.orders.commonOrders;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.penlk.dataAcessLayer.entities.orders.commonOrder.CommonOrderId;

@Getter
@AllArgsConstructor
public class CommonOrderNotFoundException extends RuntimeException {
    private final CommonOrderId id;
}
