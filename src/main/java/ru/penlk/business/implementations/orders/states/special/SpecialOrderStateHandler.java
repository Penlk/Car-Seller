package ru.penlk.business.implementations.orders.states.special;

import ru.penlk.dao.entities.orders.special.SpecialOrderState;

public interface SpecialOrderStateHandler {
    Boolean tryConfirm(SpecialOrderCore core);

    Boolean tryWaitPurchase(SpecialOrderCore core);

    Boolean tryPurchase(SpecialOrderCore core);

    Boolean tryReserve(SpecialOrderCore core);

    Boolean tryCarReadyToTake(SpecialOrderCore core);

    Boolean tryComplete(SpecialOrderCore core);

    Boolean tryCancel(SpecialOrderCore core);

    SpecialOrderState getState();
}
