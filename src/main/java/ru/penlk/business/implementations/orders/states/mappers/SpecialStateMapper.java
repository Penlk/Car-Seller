package ru.penlk.businessLayer.implementations.orders.states.mappers;

import ru.penlk.businessLayer.implementations.orders.states.special.SpecialOrderStateHandler;
import ru.penlk.dao.entities.orders.specialOrder.SpecialOrderState;

public interface SpecialStateMapper {
    SpecialOrderStateHandler map(SpecialOrderState state);
}
