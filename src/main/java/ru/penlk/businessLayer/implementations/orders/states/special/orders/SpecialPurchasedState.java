package ru.penlk.businessLayer.implementations.orders.states.special.orders;

import ru.penlk.dataAcessLayer.entities.orders.commonOrder.CommonOrderState;

public class SpecialPurchasedState extends SpecialDraftState {
    @Override
    public CommonOrderState getState() {
        return CommonOrderState.Paid;
    }

    @Override
    public Boolean tryCarReadyToTake(SpecialOrderCore core) {
        core.updateState(new SpecialCarReadyToTakeState());

        return true;
    }
}
