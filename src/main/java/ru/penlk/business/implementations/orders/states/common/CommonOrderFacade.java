package ru.penlk.business.implementations.orders.states.common;

import lombok.AllArgsConstructor;
import ru.penlk.dao.entities.orders.common.CommonOrder;
import ru.penlk.dao.entities.orders.common.CommonOrderState;
import ru.penlk.dao.entities.users.managers.Manager;

@AllArgsConstructor
public class CommonOrderFacade {
    private final CommonOrderCore core;

    public Boolean tryConfirm(Manager manager) {
        return core.getState().tryConfirm(core, manager);
    }

    public Boolean tryWaitPurchase() {
        return core.getState().tryWaitPurchase(core);
    }

    public Boolean tryPurchase() {
        return core.getState().tryPurchase(core);
    }

    public Boolean tryCarReadyToTake() {
        return core.getState().tryCarReadyToTake(core);
    }

    public Boolean tryComplete() {
        return core.getState().tryComplete(core);
    }

    public Boolean tryCancel() {
        return core.getState().tryCancel(core);
    }

    public CommonOrderState getState() {
        return core.getState().getState();
    }

    public CommonOrder getOrder() {
        return core.getOrder();
    }
}
