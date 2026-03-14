package ru.penlk.business.implementations.orders.states.special;

import ru.penlk.dao.entities.orders.specialOrder.SpecialOrderState;

public class SpecialPurchasedState extends SpecialDraftState {
    @Override
    public SpecialOrderState getState() {
        return SpecialOrderState.PAID;
    }

    @Override
    public Boolean tryCarReadyToTake(SpecialOrderCore core) {
        core.updateState(new SpecialCarReadyToTakeState());

        return true;
    }
}
