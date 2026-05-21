package ru.penlk.business.implementations.orders.states.common;

import ru.penlk.dao.entities.orders.common.CommonOrderState;

public class CommonReservedState extends CommonDraftState {
    @Override
    public CommonOrderState getState() {
        return CommonOrderState.RESERVED;
    }

    @Override
    public Boolean tryCarReadyToTake(CommonOrderCore core) {
        core.updateState(new CommonCarReadyToTakeState());

        return true;
    }
}
