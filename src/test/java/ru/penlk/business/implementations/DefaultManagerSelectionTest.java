package ru.penlk.business.implementations;

import org.junit.jupiter.api.Test;
import ru.penlk.business.implementations.orders.strategies.DefaultManagerSelection;
import ru.penlk.dao.entities.users.managers.Manager;
import ru.penlk.dao.entities.users.managers.ManagerId;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class DefaultManagerSelectionTest {
    @Test
    void findManager_single_returnsIt() {
        DefaultManagerSelection sel = new DefaultManagerSelection();
        Manager m = new Manager(new ManagerId(1));

        var opt = sel.findManager(List.of(m));

        assertTrue(opt.isPresent());
        assertTrue(opt.get().getId().id() == 1);
    }
}
