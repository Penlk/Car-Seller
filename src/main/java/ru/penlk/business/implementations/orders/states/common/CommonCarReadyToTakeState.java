package ru.penlk.business.implementations.orders.states.common;

import ru.penlk.dao.entities.orders.commonOrder.CommonOrderState;

public class CommonCarReadyToTakeState extends CommonDraftState {
    @Override
    public CommonOrderState getState() {
        return CommonOrderState.CAR_IS_READY;
    }

    @Override
    public Boolean tryComplete(CommonOrderCore core) {
        core.updateState(new CommonCompletedState());

        return true;
    }
}
