package ru.penlk.dataBase.repositories.interfaces.users.systemAdmins;

import ru.penlk.dataBase.entities.users.clients.Client;
import ru.penlk.dataBase.entities.users.clients.ClientId;
import ru.penlk.dataBase.entities.users.systemAdmins.SystemAdmin;
import ru.penlk.dataBase.entities.users.systemAdmins.SystemAdminId;

import java.util.Collection;
import java.util.Optional;

public interface SystemAdminRepository {
    Optional<SystemAdmin> findById(SystemAdminId systemAdminId);

    Collection<SystemAdmin> findAll();

    SystemAdmin create(SystemAdmin client) throws RuntimeException;

    SystemAdmin update(SystemAdmin client) throws RuntimeException;

    void delete(SystemAdminId systemAdminId) throws RuntimeException;
}
