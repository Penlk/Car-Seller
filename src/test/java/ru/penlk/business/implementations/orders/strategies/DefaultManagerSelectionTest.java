package ru.penlk.business.implementations.orders.strategies;

import org.junit.jupiter.api.Test;
import ru.penlk.dao.entities.users.managers.Manager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DefaultManagerSelectionTest {
    @Test
    void findManager_returnsOneOfProvided() {
        DefaultManagerSelection sel = new DefaultManagerSelection();

        Manager m1 = new Manager(new ru.penlk.dao.entities.users.managers.ManagerId(1));
        Manager m2 = new Manager(new ru.penlk.dao.entities.users.managers.ManagerId(2));

        Optional<Manager> found = sel.findManager(List.of(m1, m2));

        assertTrue(found.isPresent());
        assertTrue(found.get().getId().id() == 1L || found.get().getId().id() == 2L);
    }

    @Test
    void findManager_single_returnsThat() {
        DefaultManagerSelection sel = new DefaultManagerSelection();
        Manager m = new Manager(new ru.penlk.dao.entities.users.managers.ManagerId(3));

        Optional<Manager> found = sel.findManager(List.of(m));

        assertTrue(found.isPresent());
        assertEquals(3L, found.get().getId().id());
    }
}
