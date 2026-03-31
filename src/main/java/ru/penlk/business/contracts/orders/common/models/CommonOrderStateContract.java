package ru.penlk.business.contracts.orders.common.models;

import ru.penlk.dao.entities.orders.common.CommonOrderState;

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
            case CommonOrderState.ISSUED -> CommonOrderStateContract.Issued;
            case CommonOrderState.AGREED -> CommonOrderStateContract.Agreed;
            case CommonOrderState.WAITING_PAYMENT -> CommonOrderStateContract.WaitingPayment;
            case CommonOrderState.PAID -> CommonOrderStateContract.Paid;
            case CommonOrderState.CAR_IS_READY -> CommonOrderStateContract.CarIsReady;
            case CommonOrderState.CANCELED -> CommonOrderStateContract.Canceled;
            case CommonOrderState.DONE -> CommonOrderStateContract.Done;
        };
    }

    public static CommonOrderState mapToCommonOrderState(CommonOrderStateContract stateContract) {
        return switch (stateContract) {
            case CommonOrderStateContract.Issued -> CommonOrderState.ISSUED;
            case CommonOrderStateContract.Agreed -> CommonOrderState.AGREED;
            case CommonOrderStateContract.WaitingPayment -> CommonOrderState.WAITING_PAYMENT;
            case CommonOrderStateContract.Paid -> CommonOrderState.PAID;
            case CommonOrderStateContract.CarIsReady -> CommonOrderState.CAR_IS_READY;
            case CommonOrderStateContract.Canceled -> CommonOrderState.CANCELED;
            case CommonOrderStateContract.Done -> CommonOrderState.DONE;
        };
    }
}
