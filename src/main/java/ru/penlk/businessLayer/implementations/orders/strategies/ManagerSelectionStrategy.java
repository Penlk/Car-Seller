package ru.penlk.businessLayer.implementations.orders.strategies;

import ru.penlk.dataAcessLayer.entities.users.managers.Manager;

import java.util.Collection;
import java.util.Optional;

public interface ManagerSelectionStrategy {
    Optional<Manager> findManager(Collection<Manager> managers);
}
