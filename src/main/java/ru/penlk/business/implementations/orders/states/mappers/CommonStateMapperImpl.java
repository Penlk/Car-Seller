package ru.penlk.business.implementations.orders.states.mappers;

import ru.penlk.business.implementations.orders.states.common.CommonCanceledState;
import ru.penlk.business.implementations.orders.states.common.CommonCarReadyToTakeState;
import ru.penlk.business.implementations.orders.states.common.CommonCompletedState;
import ru.penlk.business.implementations.orders.states.common.CommonConfirmedState;
import ru.penlk.business.implementations.orders.states.common.CommonIssuedState;
import ru.penlk.business.implementations.orders.states.common.CommonOrderStateHandler;
import ru.penlk.business.implementations.orders.states.common.CommonPurchasedState;
import ru.penlk.business.implementations.orders.states.common.CommonWaitingPurchaseState;
import ru.penlk.dao.entities.orders.common.CommonOrderState;

public class CommonStateMapperImpl implements CommonStateMapper {
    @Override
    public CommonOrderStateHandler map(CommonOrderState state) {
        return switch (state) {
            case CommonOrderState.ISSUED -> new CommonIssuedState();
            case CommonOrderState.AGREED -> new CommonConfirmedState();
            case CommonOrderState.WAITING_PAYMENT -> new CommonWaitingPurchaseState();
            case CommonOrderState.PAID -> new CommonPurchasedState();
            case CommonOrderState.CAR_IS_READY -> new CommonCarReadyToTakeState();
            case CommonOrderState.CANCELED -> new CommonCanceledState();
            case CommonOrderState.DONE -> new CommonCompletedState();
        };
    }
}
