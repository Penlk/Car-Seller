package ru.penlk.dataBase.entities.orders.commonOrder;

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
