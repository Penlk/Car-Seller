package ru.penlk.business.implementations.orders.states.special;

import ru.penlk.dao.entities.orders.special.SpecialOrderState;
import ru.penlk.dao.entities.users.managers.Manager;

public interface SpecialOrderStateHandler {
    Boolean tryConfirm(SpecialOrderCore core, Manager manager);

    Boolean tryWaitPurchase(SpecialOrderCore core);

    Boolean tryPurchase(SpecialOrderCore core);

    Boolean tryCarReadyToTake(SpecialOrderCore core);

    Boolean tryComplete(SpecialOrderCore core);

    Boolean tryCancel(SpecialOrderCore core);

    SpecialOrderState getState();
}
