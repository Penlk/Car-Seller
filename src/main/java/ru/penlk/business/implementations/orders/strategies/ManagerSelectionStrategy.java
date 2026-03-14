package ru.penlk.business.implementations.orders.strategies;

import ru.penlk.dao.entities.users.managers.Manager;

import java.util.Collection;
import java.util.Optional;

public interface ManagerSelectionStrategy {
    Optional<Manager> findManager(Collection<Manager> managers);
}
