package ru.penlk.business.implementations.orders.states.common;

import ru.penlk.dao.entities.users.managers.ManagerId;

public abstract class CommonDraftState implements CommonOrderStateHandler {
    @Override
    public Boolean tryConfirm(CommonOrderCore core, ManagerId managerId) {
        return false;
    }

    @Override
    public Boolean tryWaitPurchase(CommonOrderCore core) {
        return false;
    }

    @Override
    public Boolean tryPurchase(CommonOrderCore core) {
        return false;
    }

    @Override
    public Boolean tryCarReadyToTake(CommonOrderCore core) {
        return false;
    }

    @Override
    public Boolean tryComplete(CommonOrderCore core) {
        return false;
    }

    @Override
    public Boolean tryCancel(CommonOrderCore core) {
        return false;
    }
}
