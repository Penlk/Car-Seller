package ru.penlk.businessLayer.implementations.orders.states.special;

import ru.penlk.dataAcessLayer.entities.orders.specialOrder.SpecialOrderState;

public class SpecialPurchasedState extends SpecialDraftState {
    @Override
    public SpecialOrderState getState() {
        return SpecialOrderState.Paid;
    }

    @Override
    public Boolean tryCarReadyToTake(SpecialOrderCore core) {
        core.updateState(new SpecialCarReadyToTakeState());

        return true;
    }
}
