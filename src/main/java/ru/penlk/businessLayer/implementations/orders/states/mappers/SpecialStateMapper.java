package ru.penlk.businessLayer.implementations.orders.states.mappers;

import ru.penlk.dataAcessLayer.entities.orders.specialOrder.SpecialOrderState;

public interface SpecialStateMapper {
    SpecialOrderStateHandler map(SpecialOrderState state);
}
