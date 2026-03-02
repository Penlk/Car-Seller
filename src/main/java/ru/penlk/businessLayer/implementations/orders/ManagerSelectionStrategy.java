package ru.penlk.businessLayer.implementations.orders;

import ru.penlk.dataAcessLayer.entities.users.managers.Manager;
import ru.penlk.dataAcessLayer.repositories.interfaces.users.managers.ManagerRepository;

import java.util.Optional;

public interface ManagerSelectionStrategy {
    Optional<Manager> findManager(ManagerRepository managerRepository);
}
