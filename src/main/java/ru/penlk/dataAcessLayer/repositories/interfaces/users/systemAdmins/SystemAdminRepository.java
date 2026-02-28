package ru.penlk.dataAcessLayer.repositories.interfaces.users.systemAdmins;

import ru.penlk.dataAcessLayer.entities.users.systemAdmins.SystemAdmin;
import ru.penlk.dataAcessLayer.entities.users.systemAdmins.SystemAdminId;

import java.util.Collection;
import java.util.Optional;

public interface SystemAdminRepository {
    Optional<SystemAdmin> findById(SystemAdminId systemAdminId);

    Collection<SystemAdmin> findAll();

    SystemAdmin create(SystemAdmin client) throws RuntimeException;

    SystemAdmin update(SystemAdmin client) throws RuntimeException;

    void delete(SystemAdminId systemAdminId) throws RuntimeException;
}
