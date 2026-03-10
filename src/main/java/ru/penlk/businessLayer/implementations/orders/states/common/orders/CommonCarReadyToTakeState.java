package ru.penlk.businessLayer.implementations.orders.states.common.orders;

import ru.penlk.dataAcessLayer.entities.orders.commonOrder.CommonOrderState;

public class CommonCarReadyToTakeState extends CommonDraftState {
    @Override
    public CommonOrderState getState() {
        return CommonOrderState.CarIsReady;
    }

    @Override
    public Boolean tryComplete(CommonOrderCore core) {
        core.updateState(new CommonCompletedState());
        
        return true;
    }
}
