package ru.penlk.dataAcessLayer.entities.orders.specialOrder;

public enum SpecialOrderState {
    Issued,
    Agreed,
    WaitingPayment,
    Paid,
    CarIsReady,
    Canceled,
    Done
}
