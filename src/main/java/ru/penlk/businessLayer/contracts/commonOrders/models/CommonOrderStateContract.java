package ru.penlk.businessLayer.contracts.commonOrders.models;

import ru.penlk.dataAcessLayer.entities.orders.commonOrder.CommonOrderState;

public enum CommonOrderStateContract {
    Issued,
    Agreed,
    WaitingPayment,
    Paid,
    CarIsReady,
    Canceled,
    Done;

    public static CommonOrderStateContract mapToContract(CommonOrderState state) {
        return switch (state) {
            case CommonOrderState.Issued -> CommonOrderStateContract.Issued;
            case CommonOrderState.Agreed -> CommonOrderStateContract.Agreed;
            case CommonOrderState.WaitingPayment -> CommonOrderStateContract.WaitingPayment;
            case CommonOrderState.Paid -> CommonOrderStateContract.Paid;
            case CommonOrderState.CarIsReady -> CommonOrderStateContract.CarIsReady;
            case CommonOrderState.Canceled -> CommonOrderStateContract.Canceled;
            case CommonOrderState.Done -> CommonOrderStateContract.Done;
        };
    }

    public static CommonOrderState mapToCommonOrderState(CommonOrderStateContract stateContract) {
        return switch (stateContract) {
            case CommonOrderStateContract.Issued -> CommonOrderState.Issued;
            case CommonOrderStateContract.Agreed -> CommonOrderState.Agreed;
            case CommonOrderStateContract.WaitingPayment -> CommonOrderState.WaitingPayment;
            case CommonOrderStateContract.Paid -> CommonOrderState.Paid;
            case CommonOrderStateContract.CarIsReady -> CommonOrderState.CarIsReady;
            case CommonOrderStateContract.Canceled -> CommonOrderState.Canceled;
            case CommonOrderStateContract.Done -> CommonOrderState.Done;
        };
    }
}
