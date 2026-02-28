package ru.penlk.dataBase.repositories.interfaces.orders.specialOrders;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.penlk.dataBase.entities.orders.specialOrder.SpecialOrderId;

@Getter
@AllArgsConstructor
public class SpecialOrderAlreadyInException extends RuntimeException {
    private final SpecialOrderId specialOrderId;
}
