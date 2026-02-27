package ru.penlk.dataBase.entities.orders.specialOrder;

public enum SpecialOrderState
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
