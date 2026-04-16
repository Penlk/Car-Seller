package ru.penlk.dao.entities.orders.common;

public enum CommonOrderState {
    PLACED,
    AGREED,
    WAITING_PAYMENT,
    PAID,
    CAR_IS_READY,
    CANCELED,
    DONE
}
