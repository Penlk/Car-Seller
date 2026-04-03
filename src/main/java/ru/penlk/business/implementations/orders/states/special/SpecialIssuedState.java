package ru.penlk.business.implementations.orders.states.special;

import ru.penlk.dao.entities.orders.special.SpecialOrderState;

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
        return SpecialOrderState.AGREED;
    }
}
