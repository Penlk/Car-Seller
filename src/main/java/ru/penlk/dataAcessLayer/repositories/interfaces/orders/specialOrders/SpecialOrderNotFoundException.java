package ru.penlk.dataAcessLayer.repositories.interfaces.orders.specialOrders;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.penlk.dataAcessLayer.entities.orders.specialOrder.SpecialOrderId;

@Getter
@AllArgsConstructor
public class SpecialOrderNotFoundException extends RuntimeException {
    private final SpecialOrderId specialOrderId;
}
