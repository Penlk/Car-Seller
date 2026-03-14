package ru.penlk.business.implementations.orders.states.special;

import ru.penlk.dao.entities.users.managers.ManagerId;

public abstract class SpecialDraftState implements SpecialOrderStateHandler {
    @Override
    public Boolean tryConfirm(SpecialOrderCore core, ManagerId managerId) {
        return false;
    }

    @Override
    public Boolean tryWaitPurchase(SpecialOrderCore core) {
        return false;
    }

    @Override
    public Boolean tryPurchase(SpecialOrderCore core) {
        return false;
    }

    @Override
    public Boolean tryCarReadyToTake(SpecialOrderCore core) {
        return false;
    }

    @Override
    public Boolean tryComplete(SpecialOrderCore core) {
        return false;
    }

    @Override
    public Boolean tryCancel(SpecialOrderCore core) {
        return false;
    }
}
