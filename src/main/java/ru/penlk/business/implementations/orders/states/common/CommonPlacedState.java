package ru.penlk.business.implementations.orders.states.common;

import ru.penlk.dao.entities.orders.common.CommonOrderState;

public class CommonPlacedState extends CommonDraftState {
    @Override
    public Boolean tryConfirm(CommonOrderCore core) {
        core.updateState(new CommonConfirmedState());

        return true;
    }

    @Override
    public Boolean tryCancel(CommonOrderCore core) {
        core.updateState(new CommonCanceledState());

        return true;
    }

    @Override
    public CommonOrderState getState() {
        return CommonOrderState.AGREED;
    }
}
