package ru.penlk.business.implementations.orders.states.special;

import ru.penlk.dao.entities.orders.special.SpecialOrderState;

public class SpecialWaitingPurchaseState extends SpecialDraftState {
    @Override
    public SpecialOrderState getState() {
        return SpecialOrderState.WAITING_PAYMENT;
    }

    @Override
    public Boolean tryPurchase(SpecialOrderCore core) {
        core.updateState(new SpecialPurchasedState());

        return true;
    }

    @Override
    public Boolean tryCancel(SpecialOrderCore core) {
        core.updateState(new SpecialCanceledState());

        return true;
    }
}
