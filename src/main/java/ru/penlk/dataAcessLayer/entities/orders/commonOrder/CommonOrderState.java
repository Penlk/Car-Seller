package ru.penlk.dataAcessLayer.entities.orders.commonOrder;

public enum CommonOrderState
{
    Draft,
    Issued,
    Agreed,
    WaitingPayment,
    Paid,
    CarIsReady,
    Canceled,
    Done
}
