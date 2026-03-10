package ru.penlk.businessLayer.implementations.orders.states.special.orders;

import ru.penlk.dataAcessLayer.entities.orders.commonOrder.CommonOrderState;

public class SpecialConfirmedState extends SpecialDraftState {
    @Override
    public CommonOrderState getState() {
        return CommonOrderState.Agreed;
    }

    @Override
    public Boolean tryWaitPurchase(SpecialOrderCore core) {
        core.updateState(new SpecialWaitingPurchaseState());

        return true;
    }

    @Override
    public Boolean tryCancel(SpecialOrderCore core) {
        core.updateState(new SpecialCanceledState());

        return true;
    }
}
