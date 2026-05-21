package ru.penlk.business.implementations.orders.states.common;

import ru.penlk.dao.entities.orders.common.CommonOrderState;

public class CommonPurchasedState extends CommonDraftState {
    @Override
    public CommonOrderState getState() {
        return CommonOrderState.PAID;
    }

    @Override
    public Boolean tryReserve(CommonOrderCore core) {
        core.updateState(new CommonReservedState());

        return true;
    }

    @Override
    public Boolean tryCancel(CommonOrderCore core) {
        core.updateState(new CommonCanceledState());

        return true;
    }
}
