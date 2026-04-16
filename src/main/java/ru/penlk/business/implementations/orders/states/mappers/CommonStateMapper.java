package ru.penlk.business.implementations.orders.states.mappers;

import ru.penlk.business.implementations.orders.states.common.CommonOrderStateHandler;
import ru.penlk.dao.entities.orders.common.CommonOrderState;

public interface CommonStateMapper {
    CommonOrderStateHandler map(CommonOrderState state);
}
