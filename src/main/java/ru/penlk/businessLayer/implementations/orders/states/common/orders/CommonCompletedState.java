package ru.penlk.businessLayer.implementations.orders.states.common.orders;

import ru.penlk.dataAcessLayer.entities.orders.commonOrder.CommonOrderState;

public class CommonCompletedState extends CommonDraftState {
    @Override
    public CommonOrderState getState() {
        return CommonOrderState.Done;
    }
}
