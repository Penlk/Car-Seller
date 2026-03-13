package ru.penlk.dataAcessLayer.entities.orders.specialOrder;

public enum SpecialOrderState {
    ISSUED,
    AGREED,
    WAITING_PAYMENT,
    PAID,
    CAR_IS_READY,
    CANCELED,
    DONE
}
