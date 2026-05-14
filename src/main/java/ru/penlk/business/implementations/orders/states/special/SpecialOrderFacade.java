package ru.penlk.business.implementations.orders.states.special;

import lombok.AllArgsConstructor;
import ru.penlk.dao.entities.orders.special.SpecialOrder;
import ru.penlk.dao.entities.orders.special.SpecialOrderState;

@AllArgsConstructor
public class SpecialOrderFacade {
    private final SpecialOrderCore core;

    public Boolean tryConfirm() {
        return core.getState().tryConfirm(core);
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

    public SpecialOrderState getState() {
        return core.getState().getState();
    }

    public SpecialOrder getOrder() {
        return core.getOrder();
    }
}
