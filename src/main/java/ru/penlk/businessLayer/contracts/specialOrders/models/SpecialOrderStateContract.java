package ru.penlk.businessLayer.contracts.specialOrders.models;

import ru.penlk.dataAcessLayer.entities.orders.specialOrder.SpecialOrderState;

public enum SpecialOrderStateContract {
    Issued,
    Agreed,
    WaitingPayment,
    Paid,
    CarIsReady,
    Canceled,
    Done;

    public static SpecialOrderStateContract mapToContract(SpecialOrderState state) {
        return switch (state) {
            case SpecialOrderState.Issued -> SpecialOrderStateContract.Issued;
            case SpecialOrderState.Agreed -> SpecialOrderStateContract.Agreed;
            case SpecialOrderState.WaitingPayment -> SpecialOrderStateContract.WaitingPayment;
            case SpecialOrderState.Paid -> SpecialOrderStateContract.Paid;
            case SpecialOrderState.CarIsReady -> SpecialOrderStateContract.CarIsReady;
            case SpecialOrderState.Canceled -> SpecialOrderStateContract.Canceled;
            case SpecialOrderState.Done -> SpecialOrderStateContract.Done;
        };
    }

    public static SpecialOrderState mapToSpecialOrderState(SpecialOrderStateContract stateContract) {
        return switch (stateContract) {
            case SpecialOrderStateContract.Issued -> SpecialOrderState.Issued;
            case SpecialOrderStateContract.Agreed -> SpecialOrderState.Agreed;
            case SpecialOrderStateContract.WaitingPayment -> SpecialOrderState.WaitingPayment;
            case SpecialOrderStateContract.Paid -> SpecialOrderState.Paid;
            case SpecialOrderStateContract.CarIsReady -> SpecialOrderState.CarIsReady;
            case SpecialOrderStateContract.Canceled -> SpecialOrderState.Canceled;
            case SpecialOrderStateContract.Done -> SpecialOrderState.Done;
        };
    }
}
