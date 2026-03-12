package ru.penlk.businessLayer.implementations.orders.states.mappers;

import ru.penlk.businessLayer.implementations.orders.states.common.CommonCanceledState;
import ru.penlk.businessLayer.implementations.orders.states.common.CommonCarReadyToTakeState;
import ru.penlk.businessLayer.implementations.orders.states.common.CommonCompletedState;
import ru.penlk.businessLayer.implementations.orders.states.common.CommonConfirmedState;
import ru.penlk.businessLayer.implementations.orders.states.common.CommonIssuedState;
import ru.penlk.businessLayer.implementations.orders.states.common.CommonOrderStateHandler;
import ru.penlk.businessLayer.implementations.orders.states.common.CommonPurchasedState;
import ru.penlk.businessLayer.implementations.orders.states.common.CommonWaitingPurchaseState;
import ru.penlk.dataAcessLayer.entities.orders.commonOrder.CommonOrderState;

public class CommonStateMapperImpl implements CommonStateMapper {
    @Override
    public CommonOrderStateHandler map(CommonOrderState state) {
        return switch (state) {
            case CommonOrderState.Issued -> new CommonIssuedState();
            case CommonOrderState.Agreed -> new CommonConfirmedState();
            case CommonOrderState.WaitingPayment -> new CommonWaitingPurchaseState();
            case CommonOrderState.Paid -> new CommonPurchasedState();
            case CommonOrderState.CarIsReady -> new CommonCarReadyToTakeState();
            case CommonOrderState.Canceled -> new CommonCanceledState();
            case CommonOrderState.Done -> new CommonCompletedState();
        };
    }
}
