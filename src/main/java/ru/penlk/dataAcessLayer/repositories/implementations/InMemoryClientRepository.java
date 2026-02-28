package ru.penlk.dataAcessLayer.repositories.implementations;

import ru.penlk.dataAcessLayer.entities.users.clients.Client;
import ru.penlk.dataAcessLayer.entities.users.clients.ClientId;
import ru.penlk.dataAcessLayer.repositories.interfaces.users.clients.ClientRepository;

import java.util.*;

public class InMemoryClientRepository implements ClientRepository {
    Map<ClientId, Client> clients = new HashMap<>();
    int lastIndex = 0;


    @Override
    public Optional<Client> findById(ClientId id) {
        return Optional.ofNullable(clients.get(id));
    }

    @Override
    public Collection<Client> findAll() {
        return Collections.unmodifiableCollection(clients.values());
    }

    @Override
    public void delete(ClientId id) throws RuntimeException {
        if (clients.remove(id) == null) {

            throw new RuntimeException("Temporary exception about client");
        }
    }

    @Override
    public Client update(Client client) throws RuntimeException {
        if (!clients.containsKey(client.getId())) {
            throw new RuntimeException("Temporary exception about client");
        }

        return clients.get(client.getId());
    }

    @Override
    public Client create(Client client) {
        ++lastIndex;
        return clients.put(new ClientId(lastIndex), client);
    }
}
