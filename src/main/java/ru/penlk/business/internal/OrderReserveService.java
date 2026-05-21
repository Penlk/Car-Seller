package ru.penlk.business.internal;

import ru.penlk.dao.entities.outbox.OrderType;

public interface OrderReserveService {
    void reserve(Long orderId, OrderType orderType) throws RuntimeException;
}
