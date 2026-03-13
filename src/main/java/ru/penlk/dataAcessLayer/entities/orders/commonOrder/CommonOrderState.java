package ru.penlk.dataAcessLayer.entities.orders.commonOrder;

public enum CommonOrderState {
    ISSUED,
    AGREED,
    WAITING_PAYMENT,
    PAID,
    CAR_IS_READY,
    CANCELED,
    DONE
}
