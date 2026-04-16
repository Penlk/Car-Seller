package ru.penlk.business.implementations.orders.states.mappers;

import ru.penlk.business.implementations.orders.states.special.SpecialOrderStateHandler;
import ru.penlk.dao.entities.orders.special.SpecialOrderState;

public interface SpecialStateMapper {
    SpecialOrderStateHandler map(SpecialOrderState state);
}
