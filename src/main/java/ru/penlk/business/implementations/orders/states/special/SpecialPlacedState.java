package ru.penlk.business.implementations.orders.states.special;

import ru.penlk.dao.entities.orders.special.SpecialOrderState;
import ru.penlk.dao.entities.users.managers.Manager;

public class SpecialPlacedState extends SpecialDraftState {
    @Override
    public Boolean tryConfirm(SpecialOrderCore core, Manager manager) {
        core.confirm(manager);
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
