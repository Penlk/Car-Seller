package ru.penlk.businessLayer.implementations.orders.states.mappers;

import ru.penlk.businessLayer.implementations.orders.states.common.CommonOrderStateHandler;
import ru.penlk.dao.entities.orders.commonOrder.CommonOrderState;

public interface CommonStateMapper {
    CommonOrderStateHandler map(CommonOrderState state);
}
