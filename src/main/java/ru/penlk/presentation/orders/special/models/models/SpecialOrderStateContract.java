package ru.penlk.business.contracts.orders.special.models;

import ru.penlk.dao.entities.orders.special.SpecialOrderState;

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
            case SpecialOrderState.ISSUED -> SpecialOrderStateContract.Issued;
            case SpecialOrderState.AGREED -> SpecialOrderStateContract.Agreed;
            case SpecialOrderState.WAITING_PAYMENT -> SpecialOrderStateContract.WaitingPayment;
            case SpecialOrderState.PAID -> SpecialOrderStateContract.Paid;
            case SpecialOrderState.CAR_IS_READY -> SpecialOrderStateContract.CarIsReady;
            case SpecialOrderState.CANCELED -> SpecialOrderStateContract.Canceled;
            case SpecialOrderState.DONE -> SpecialOrderStateContract.Done;
        };
    }

    public static SpecialOrderState mapToSpecialOrderState(SpecialOrderStateContract stateContract) {
        return switch (stateContract) {
            case SpecialOrderStateContract.Issued -> SpecialOrderState.ISSUED;
            case SpecialOrderStateContract.Agreed -> SpecialOrderState.AGREED;
            case SpecialOrderStateContract.WaitingPayment -> SpecialOrderState.WAITING_PAYMENT;
            case SpecialOrderStateContract.Paid -> SpecialOrderState.PAID;
            case SpecialOrderStateContract.CarIsReady -> SpecialOrderState.CAR_IS_READY;
            case SpecialOrderStateContract.Canceled -> SpecialOrderState.CANCELED;
            case SpecialOrderStateContract.Done -> SpecialOrderState.DONE;
        };
    }
}
