package ru.penlk.dataBase.repositories.interfaces.users.compositionAdmins;

import ru.penlk.dataBase.entities.users.clients.Client;
import ru.penlk.dataBase.entities.users.clients.ClientId;
import ru.penlk.dataBase.entities.users.compositionAdmins.CompositionAdmin;
import ru.penlk.dataBase.entities.users.compositionAdmins.CompositionAdminId;

import java.util.Collection;
import java.util.Optional;

public interface CompositionAdminRepository {
    Optional<CompositionAdmin> findById(CompositionAdminId compositionAdminId);

    Collection<CompositionAdmin> findAll();

    CompositionAdmin create(CompositionAdmin compositionAdmin) throws RuntimeException;

    CompositionAdmin update(CompositionAdmin compositionAdmin) throws RuntimeException;

    void delete(CompositionAdminId compositionAdminId) throws RuntimeException;
}
