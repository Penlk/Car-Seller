package ru.penlk.dataBase.repositories.interfaces.users.clients;

import ru.penlk.dataBase.entities.orders.specialOrder.SpecialOrderId;
import ru.penlk.dataBase.entities.users.clients.Client;
import ru.penlk.dataBase.entities.users.clients.ClientId;

import java.util.Collection;
import java.util.Optional;

public interface ClientRepository {
    Optional<Client> findById(ClientId clientId);
    Collection<Client> findAll();
    Client create(Client client) throws RuntimeException;
    Client update(Client client) throws RuntimeException;
    void delete(ClientId clientId) throws RuntimeException;
}
