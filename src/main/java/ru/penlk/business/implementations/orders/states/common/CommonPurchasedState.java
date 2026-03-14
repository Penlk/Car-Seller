package ru.penlk.businessLayer.implementations.orders.states.common;

import ru.penlk.dao.entities.orders.commonOrder.CommonOrderState;

public class CommonPurchasedState extends CommonDraftState {
    @Override
    public CommonOrderState getState() {
        return CommonOrderState.PAID;
    }

    @Override
    public Boolean tryCarReadyToTake(CommonOrderCore core) {
        core.updateState(new CommonCarReadyToTakeState());

        return true;
    }
}
