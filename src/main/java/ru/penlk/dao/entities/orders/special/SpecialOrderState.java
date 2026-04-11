package ru.penlk.dao.entities.orders.special;

public enum SpecialOrderState {
    PLACED,
    AGREED,
    WAITING_PAYMENT,
    PAID,
    CAR_IS_READY,
    CANCELED,
    DONE
}
