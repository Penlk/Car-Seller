package ru.penlk.businessLayer.implementations.orders.states.common;

import ru.penlk.dao.entities.orders.commonOrder.CommonOrderState;

public class CommonConfirmedState extends CommonDraftState {
    @Override
    public CommonOrderState getState() {
        return CommonOrderState.AGREED;
    }

    @Override
    public Boolean tryWaitPurchase(CommonOrderCore core) {
        core.updateState(new CommonWaitingPurchaseState());

        return true;
    }

    @Override
    public Boolean tryCancel(CommonOrderCore core) {
        core.updateState(new CommonCanceledState());

        return true;
    }
}
