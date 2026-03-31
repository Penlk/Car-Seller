package ru.penlk.business.implementations.orders.states.special;

import ru.penlk.dao.entities.orders.special.SpecialOrderState;
import ru.penlk.dao.entities.users.managers.ManagerId;

public interface SpecialOrderStateHandler {
    Boolean tryConfirm(SpecialOrderCore core, ManagerId managerId);

    Boolean tryWaitPurchase(SpecialOrderCore core);

    Boolean tryPurchase(SpecialOrderCore core);

    Boolean tryCarReadyToTake(SpecialOrderCore core);

    Boolean tryComplete(SpecialOrderCore core);

    Boolean tryCancel(SpecialOrderCore core);

    SpecialOrderState getState();
}
