package ru.penlk.businessLayer.implementations.orders.states.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.penlk.dao.entities.orders.commonOrder.CommonOrder;
import ru.penlk.dao.entities.users.managers.ManagerId;

@AllArgsConstructor
@Getter
public class CommonOrderCore {
    private CommonOrder order;
    private CommonOrderStateHandler state;

    public void updateState(CommonOrderStateHandler state) {
        this.state = state;
        order.setState(state.getState());
    }

    public void confirm(ManagerId managerId) {
        order.setManagerId(managerId);
    }
}
