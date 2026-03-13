package ru.penlk.businessLayer.implementations.orders.states.common;

import ru.penlk.dao.entities.orders.commonOrder.CommonOrderState;

public class CommonWaitingPurchaseState extends CommonDraftState {
    @Override
    public CommonOrderState getState() {
        return CommonOrderState.WAITING_PAYMENT;
    }

    @Override
    public Boolean tryPurchase(CommonOrderCore core) {
        core.updateState(new CommonPurchasedState());

        return true;
    }

    @Override
    public Boolean tryCancel(CommonOrderCore core) {
        core.updateState(new CommonCanceledState());

        return true;
    }
}
