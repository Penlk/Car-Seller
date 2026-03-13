package ru.penlk.businessLayer.implementations.orders.states.special;

import ru.penlk.dataAcessLayer.entities.orders.specialOrder.SpecialOrderState;

public class SpecialCompletedState extends SpecialDraftState {
    @Override
    public SpecialOrderState getState() {
        return SpecialOrderState.DONE;
    }
}
