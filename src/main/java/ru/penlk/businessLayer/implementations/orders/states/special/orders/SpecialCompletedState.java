package ru.penlk.businessLayer.implementations.orders.states.special.orders;

import ru.penlk.dataAcessLayer.entities.orders.commonOrder.CommonOrderState;

public class SpecialCompletedState extends SpecialDraftState {
    @Override
    public CommonOrderState getState() {
        return CommonOrderState.Done;
    }
}
