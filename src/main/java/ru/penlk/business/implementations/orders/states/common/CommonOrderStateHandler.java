package ru.penlk.businessLayer.implementations.orders.states.common;

import ru.penlk.dao.entities.orders.commonOrder.CommonOrderState;
import ru.penlk.dao.entities.users.managers.ManagerId;

public interface CommonOrderStateHandler {
    Boolean tryConfirm(CommonOrderCore core, ManagerId managerId);

    Boolean tryWaitPurchase(CommonOrderCore core);

    Boolean tryPurchase(CommonOrderCore core);

    Boolean tryCarReadyToTake(CommonOrderCore core);

    Boolean tryComplete(CommonOrderCore core);

    Boolean tryCancel(CommonOrderCore core);

    CommonOrderState getState();
}
