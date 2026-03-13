package ru.penlk.businessLayer.implementations.orders.states.mappers;

import ru.penlk.businessLayer.implementations.orders.states.special.SpecialCanceledState;
import ru.penlk.businessLayer.implementations.orders.states.special.SpecialCarReadyToTakeState;
import ru.penlk.businessLayer.implementations.orders.states.special.SpecialCompletedState;
import ru.penlk.businessLayer.implementations.orders.states.special.SpecialConfirmedState;
import ru.penlk.businessLayer.implementations.orders.states.special.SpecialIssuedState;
import ru.penlk.businessLayer.implementations.orders.states.special.SpecialOrderStateHandler;
import ru.penlk.businessLayer.implementations.orders.states.special.SpecialPurchasedState;
import ru.penlk.businessLayer.implementations.orders.states.special.SpecialWaitingPurchaseState;
import ru.penlk.dataAcessLayer.entities.orders.specialOrder.SpecialOrderState;

public class SpecialStateMapperImpl implements SpecialStateMapper {
    @Override
    public SpecialOrderStateHandler map(SpecialOrderState state) {
        return switch (state) {
            case SpecialOrderState.Issued -> new SpecialIssuedState();
            case SpecialOrderState.Agreed -> new SpecialConfirmedState();
            case SpecialOrderState.WaitingPayment -> new SpecialWaitingPurchaseState();
            case SpecialOrderState.Paid -> new SpecialPurchasedState();
            case SpecialOrderState.CarIsReady -> new SpecialCarReadyToTakeState();
            case SpecialOrderState.Canceled -> new SpecialCanceledState();
            case SpecialOrderState.Done -> new SpecialCompletedState();
        };
    }
}
