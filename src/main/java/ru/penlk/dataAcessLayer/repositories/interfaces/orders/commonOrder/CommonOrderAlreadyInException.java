package ru.penlk.dataAcessLayer.repositories.interfaces.orders.commonOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.penlk.dataAcessLayer.entities.orders.commonOrder.CommonOrderId;

@Getter
@AllArgsConstructor
public class CommonOrderAlreadyInException extends RuntimeException {
    private final CommonOrderId id;
}