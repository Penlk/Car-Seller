package ru.penlk.business.implementations.orders.states.special;


import ru.penlk.dao.entities.orders.special.SpecialOrderState;

public class SpecialCanceledState extends SpecialDraftState {

    @Override
    public SpecialOrderState getState() {
        return SpecialOrderState.CANCELED;
    }
}