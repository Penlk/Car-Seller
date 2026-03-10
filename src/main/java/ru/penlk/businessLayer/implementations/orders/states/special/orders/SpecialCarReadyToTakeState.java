package ru.penlk.businessLayer.implementations.orders.states.special.orders;

import ru.penlk.dataAcessLayer.entities.orders.commonOrder.CommonOrderState;

public class SpecialCarReadyToTakeState extends SpecialDraftState {
    @Override
    public CommonOrderState getState() {
        return CommonOrderState.CarIsReady;
    }

    @Override
    public Boolean tryComplete(SpecialOrderCore core) {
        core.updateState(new SpecialCompletedState());
        
        return true;
    }
}
