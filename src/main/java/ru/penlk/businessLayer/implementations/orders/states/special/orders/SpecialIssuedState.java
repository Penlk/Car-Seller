package ru.penlk.businessLayer.implementations.orders.states.special.orders;

import ru.penlk.dataAcessLayer.entities.orders.commonOrder.CommonOrderState;
import ru.penlk.dataAcessLayer.entities.users.managers.ManagerId;

public class SpecialIssuedState extends SpecialDraftState {
    @Override
    public Boolean tryConfirm(SpecialOrderCore core, ManagerId managerId) {
        core.confirm(managerId);
        core.updateState(new SpecialConfirmedState());

        return true;
    }

    @Override
    public Boolean tryCancel(SpecialOrderCore core) {
        core.updateState(new SpecialCanceledState());

        return true;
    }

    @Override
    public CommonOrderState getState() {
        return CommonOrderState.Agreed;
    }
}
