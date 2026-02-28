package ru.penlk.dataBase.repositories.interfaces.orders.commonOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.penlk.dataBase.entities.orders.commonOrder.CommonOrderId;

@Getter
@AllArgsConstructor
public class CommonOrderNotFoundException extends RuntimeException {
    private final CommonOrderId id;
}
