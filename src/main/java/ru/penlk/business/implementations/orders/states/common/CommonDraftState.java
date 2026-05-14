package ru.penlk.business.implementations.orders.states.common;

public abstract class CommonDraftState implements CommonOrderStateHandler {
    @Override
    public Boolean tryConfirm(CommonOrderCore core) {
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
