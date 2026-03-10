package ru.penlk.businessLayer.implementations.orders.states.mappers;

import ru.penlk.businessLayer.implementations.orders.states.common.orders.CommonOrderStateHandler;
import ru.penlk.dataAcessLayer.entities.orders.specialOrder.SpecialOrderState;

public interface StateMapper {
    CommonOrderStateHandler map(ru.penlk.dataAcessLayer.entities.orders.commonOrder.CommonOrderState state);

    CommonOrderStateHandler map(SpecialOrderState state);
}
