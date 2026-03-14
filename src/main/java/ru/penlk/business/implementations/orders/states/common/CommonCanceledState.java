package ru.penlk.business.implementations.orders.states.common;

import ru.penlk.dao.entities.orders.commonOrder.CommonOrderState;

public class CommonCanceledState extends CommonDraftState {

    @Override
    public CommonOrderState getState() {
        return CommonOrderState.CANCELED;
    }
}