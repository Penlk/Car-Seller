package ru.penlk.dao.repositories.interfaces.orders.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.penlk.dao.entities.orders.common.CommonOrderId;

@Getter
@AllArgsConstructor
public class CommonOrderNotFoundException extends RuntimeException {
    private final CommonOrderId id;
}
