package ru.penlk.businessLayer.implementations.orders.states.special;


import ru.penlk.dao.entities.orders.specialOrder.SpecialOrderState;

public class SpecialCanceledState extends SpecialDraftState {

    @Override
    public SpecialOrderState getState() {
        return SpecialOrderState.CANCELED;
    }
}