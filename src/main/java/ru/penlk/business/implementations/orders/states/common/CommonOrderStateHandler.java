package ru.penlk.business.implementations.orders.states.common;

import ru.penlk.dao.entities.orders.common.CommonOrderState;

public interface CommonOrderStateHandler {
    Boolean tryConfirm(CommonOrderCore core);

    Boolean tryWaitPurchase(CommonOrderCore core);

    Boolean tryPurchase(CommonOrderCore core);

    Boolean tryCarReadyToTake(CommonOrderCore core);

    Boolean tryComplete(CommonOrderCore core);

    Boolean tryCancel(CommonOrderCore core);

    CommonOrderState getState();
}
