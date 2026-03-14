package ru.penlk.businessLayer.implementations.orders.states.special;

import ru.penlk.dao.entities.orders.specialOrder.SpecialOrderState;

public class SpecialConfirmedState extends SpecialDraftState {
    @Override
    public SpecialOrderState getState() {
        return SpecialOrderState.AGREED;
    }

    @Override
    public Boolean tryWaitPurchase(SpecialOrderCore core) {
        core.updateState(new SpecialWaitingPurchaseState());

        return true;
    }

    @Override
    public Boolean tryCancel(SpecialOrderCore core) {
        core.updateState(new SpecialCanceledState());

        return true;
    }
}
