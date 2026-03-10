package ru.penlk.businessLayer.implementations.orders.states.common.orders;

import ru.penlk.dataAcessLayer.entities.orders.commonOrder.CommonOrderState;

public class CommonWaitingPurchaseState extends CommonDraftState {
    @Override
    public CommonOrderState getState() {
        return CommonOrderState.WaitingPayment;
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
