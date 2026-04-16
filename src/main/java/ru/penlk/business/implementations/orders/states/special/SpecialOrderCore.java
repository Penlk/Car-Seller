package ru.penlk.business.implementations.orders.states.special;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.penlk.dao.entities.orders.special.SpecialOrder;
import ru.penlk.dao.entities.users.managers.Manager;

@AllArgsConstructor
@Getter
public class SpecialOrderCore {
    private SpecialOrder order;
    private SpecialOrderStateHandler state;

    public void updateState(SpecialOrderStateHandler state) {
        this.state = state;
        order.setState(state.getState());
    }

    public void confirm(Manager manager) {
        order.setManager(manager);
    }
}
