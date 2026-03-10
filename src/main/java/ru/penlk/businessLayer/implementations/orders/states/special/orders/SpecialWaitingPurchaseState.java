package ru.penlk.businessLayer.implementations.orders.states.special.orders;

import ru.penlk.dataAcessLayer.entities.orders.commonOrder.CommonOrderState;

public class SpecialWaitingPurchaseState extends SpecialDraftState {
    @Override
    public CommonOrderState getState() {
        return CommonOrderState.WaitingPayment;
    }

    @Override
    public Boolean tryPurchase(SpecialOrderCore core) {
        core.updateState(new SpecialPurchasedState());

        return true;
    }

    @Override
    public Boolean tryCancel(SpecialOrderCore core) {
        core.updateState(new SpecialCanceledState());

        return true;
    }
}
