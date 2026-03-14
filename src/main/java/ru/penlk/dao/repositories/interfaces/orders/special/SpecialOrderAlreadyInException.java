package ru.penlk.dao.repositories.interfaces.orders.special;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.penlk.dao.entities.orders.specialOrder.SpecialOrderId;

@Getter
@AllArgsConstructor
public class SpecialOrderAlreadyInException extends RuntimeException {
    private final SpecialOrderId specialOrderId;
}
