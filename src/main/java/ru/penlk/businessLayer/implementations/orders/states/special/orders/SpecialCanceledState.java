package ru.penlk.businessLayer.implementations.orders.states.special.orders;

import ru.penlk.dataAcessLayer.entities.orders.commonOrder.CommonOrderState;

public class SpecialCanceledState extends SpecialDraftState {

    @Override
    public CommonOrderState getState() {
        return CommonOrderState.Canceled;
    }
}