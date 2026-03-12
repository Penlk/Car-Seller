package ru.penlk.businessLayer.implementations.orders.states.common;

import lombok.AllArgsConstructor;
import ru.penlk.dataAcessLayer.entities.orders.commonOrder.CommonOrder;
import ru.penlk.dataAcessLayer.entities.orders.commonOrder.CommonOrderState;
import ru.penlk.dataAcessLayer.entities.users.managers.ManagerId;

@AllArgsConstructor
public class CommonOrderFacade {
    private final CommonOrderCore core;

    public Boolean tryConfirm(ManagerId managerId) {
        return core.getState().tryConfirm(core, managerId);
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
