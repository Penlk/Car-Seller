package ru.penlk.businessLayer.implementations.orders.states.common;

import ru.penlk.dataAcessLayer.entities.orders.commonOrder.CommonOrderState;
import ru.penlk.dataAcessLayer.entities.users.managers.ManagerId;

public class CommonIssuedState extends CommonDraftState {
    @Override
    public Boolean tryConfirm(CommonOrderCore core, ManagerId managerId) {
        core.confirm(managerId);
        core.updateState(new CommonConfirmedState());

        return true;
    }

    @Override
    public Boolean tryCancel(CommonOrderCore core) {
        core.updateState(new CommonCanceledState());

        return true;
    }

    @Override
    public CommonOrderState getState() {
        return CommonOrderState.AGREED;
    }
}
