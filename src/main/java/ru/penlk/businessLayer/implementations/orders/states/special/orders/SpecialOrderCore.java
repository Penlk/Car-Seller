package ru.penlk.businessLayer.implementations.orders.states.special.orders;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.penlk.dataAcessLayer.entities.orders.specialOrder.SpecialOrder;
import ru.penlk.dataAcessLayer.entities.users.managers.ManagerId;

@AllArgsConstructor
@Getter
public class SpecialOrderCore {
    private SpecialOrder order;
    private SpecialOrderStateHandler state;

    public void updateState(SpecialOrderStateHandler state) {
        this.state = state;
        order.setState(state.getState());
    }

    public void confirm(ManagerId managerId) {
        order.setManagerId(managerId);
    }
}
