package ru.penlk.dataAcessLayer.entities.orders.commonOrder;

public enum CommonOrderState {
    Issued,
    Agreed,
    WaitingPayment,
    Paid,
    CarIsReady,
    Canceled,
    Done
}
