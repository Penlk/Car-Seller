package ru.penlk.businessLayer.implementations.orders.states.special;

import ru.penlk.dataAcessLayer.entities.orders.specialOrder.SpecialOrderState;
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
    public SpecialOrderState getState() {
        return SpecialOrderState.Agreed;
    }
}
