package ru.penlk.dataAcessLayer.repositories.interfaces.users.managers;

import ru.penlk.dataAcessLayer.entities.users.managers.Manager;
import ru.penlk.dataAcessLayer.entities.users.managers.ManagerId;

import java.util.Collection;
import java.util.Optional;

public interface ManagerRepository {
    Optional<Manager> findById(ManagerId managerId);

    Collection<Manager> findAll();

    Manager create(Manager client) throws RuntimeException;

    Manager update(Manager client) throws RuntimeException;

    void delete(ManagerId managerId) throws RuntimeException;
}
