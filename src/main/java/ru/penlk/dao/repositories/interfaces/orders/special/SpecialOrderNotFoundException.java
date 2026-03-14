package ru.penlk.dao.repositories.interfaces.orders.special;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.penlk.dao.entities.orders.specialOrder.SpecialOrderId;

@Getter
@AllArgsConstructor
public class SpecialOrderNotFoundException extends RuntimeException {
    private final SpecialOrderId id;
}
