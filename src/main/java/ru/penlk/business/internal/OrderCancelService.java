package ru.penlk.business.internal;

import ru.penlk.dao.entities.outbox.OrderType;

public interface OrderCancelService {
    void cancel(Long orderId, OrderType orderType) throws RuntimeException;
}
