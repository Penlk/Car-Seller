package ru.penlk.dao.entities.orders.common;

public enum CommonOrderState {
    ISSUED,
    AGREED,
    WAITING_PAYMENT,
    PAID,
    CAR_IS_READY,
    CANCELED,
    DONE
}
