package ru.penlk.business.implementations.orders.states.mappers;

import ru.penlk.business.implementations.orders.states.special.SpecialCanceledState;
import ru.penlk.business.implementations.orders.states.special.SpecialCarReadyToTakeState;
import ru.penlk.business.implementations.orders.states.special.SpecialCompletedState;
import ru.penlk.business.implementations.orders.states.special.SpecialConfirmedState;
import ru.penlk.business.implementations.orders.states.special.SpecialIssuedState;
import ru.penlk.business.implementations.orders.states.special.SpecialOrderStateHandler;
import ru.penlk.business.implementations.orders.states.special.SpecialPurchasedState;
import ru.penlk.business.implementations.orders.states.special.SpecialWaitingPurchaseState;
import ru.penlk.dao.entities.orders.specialOrder.SpecialOrderState;

public class SpecialStateMapperImpl implements SpecialStateMapper {
    @Override
    public SpecialOrderStateHandler map(SpecialOrderState state) {
        return switch (state) {
            case SpecialOrderState.ISSUED -> new SpecialIssuedState();
            case SpecialOrderState.AGREED -> new SpecialConfirmedState();
            case SpecialOrderState.WAITING_PAYMENT -> new SpecialWaitingPurchaseState();
            case SpecialOrderState.PAID -> new SpecialPurchasedState();
            case SpecialOrderState.CAR_IS_READY -> new SpecialCarReadyToTakeState();
            case SpecialOrderState.CANCELED -> new SpecialCanceledState();
            case SpecialOrderState.DONE -> new SpecialCompletedState();
        };
    }
}
