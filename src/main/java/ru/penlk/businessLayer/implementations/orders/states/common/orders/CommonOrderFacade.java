package ru.penlk.businessLayer.implementations.orders.states.common.orders;

import lombok.AllArgsConstructor;
import ru.penlk.dataAcessLayer.entities.orders.commonOrder.CommonOrderState;
import ru.penlk.dataAcessLayer.entities.users.managers.ManagerId;

@AllArgsConstructor
public class CommonOrderFacade {
    private final CommonOrderCore core;

    Boolean tryConfirm(CommonOrderCore core, ManagerId managerId) {
        return core.getState().tryConfirm(core, managerId);
    }

    Boolean tryWaitPurchase(CommonOrderCore core) {
        return core.getState().tryWaitPurchase(core);
    }

    Boolean tryPurchase(CommonOrderCore core) {
        return core.getState().tryPurchase(core);
    }

    Boolean tryCarReadyToTake(CommonOrderCore core) {
        return core.getState().tryCarReadyToTake(core);
    }

    Boolean tryComplete(CommonOrderCore core) {
        return core.getState().tryComplete(core);
    }

    Boolean tryCancel(CommonOrderCore core) {
        return core.getState().tryCancel(core);
    }

    CommonOrderState getState(CommonOrderCore core) {
        return core.getState().getState();
    }
}
