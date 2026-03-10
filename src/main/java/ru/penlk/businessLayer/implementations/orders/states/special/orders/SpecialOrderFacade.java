package ru.penlk.businessLayer.implementations.orders.states.special.orders;

import lombok.AllArgsConstructor;
import ru.penlk.dataAcessLayer.entities.orders.commonOrder.CommonOrderState;
import ru.penlk.dataAcessLayer.entities.orders.specialOrder.SpecialOrderState;
import ru.penlk.dataAcessLayer.entities.users.managers.ManagerId;

@AllArgsConstructor
public class SpecialOrderFacade {
    private final SpecialOrderCore core;

    Boolean tryConfirm(SpecialOrderCore core, ManagerId managerId) {
        return core.getState().tryConfirm(core, managerId);
    }

    Boolean tryWaitPurchase(SpecialOrderCore core) {
        return core.getState().tryWaitPurchase(core);
    }

    Boolean tryPurchase(SpecialOrderCore core) {
        return core.getState().tryPurchase(core);
    }

    Boolean tryCarReadyToTake(SpecialOrderCore core) {
        return core.getState().tryCarReadyToTake(core);
    }

    Boolean tryComplete(SpecialOrderCore core) {
        return core.getState().tryComplete(core);
    }

    Boolean tryCancel(SpecialOrderCore core) {
        return core.getState().tryCancel(core);
    }

    SpecialOrderState getState(SpecialOrderCore core) {
        return core.getState().getState();
    }
}
