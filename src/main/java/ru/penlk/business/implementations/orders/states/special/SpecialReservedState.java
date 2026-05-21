package ru.penlk.business.implementations.orders.states.special;

import ru.penlk.dao.entities.orders.special.SpecialOrderState;

public class SpecialReservedState extends SpecialDraftState {
    @Override
    public SpecialOrderState getState() {
        return SpecialOrderState.RESERVED;
    }

    @Override
    public Boolean tryCarReadyToTake(SpecialOrderCore core) {
        core.updateState(new SpecialCarReadyToTakeState());
        return true;
    }
}
