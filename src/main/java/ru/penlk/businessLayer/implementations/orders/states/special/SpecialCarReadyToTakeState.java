package ru.penlk.businessLayer.implementations.orders.states.special;

import ru.penlk.dataAcessLayer.entities.orders.specialOrder.SpecialOrderState;

public class SpecialCarReadyToTakeState extends SpecialDraftState {
    @Override
    public SpecialOrderState getState() {
        return SpecialOrderState.CarIsReady;
    }

    @Override
    public Boolean tryComplete(SpecialOrderCore core) {
        core.updateState(new SpecialCompletedState());
        
        return true;
    }
}
