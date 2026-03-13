package ru.penlk.dao.repositories.implementations;


import ru.penlk.dao.entities.users.managers.Manager;
import ru.penlk.dao.entities.users.managers.ManagerId;
import ru.penlk.dao.repositories.interfaces.users.managers.ManagerRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryManagerRepository implements ManagerRepository {
    Map<ManagerId, Manager> managers = new HashMap<>();
    int lastIndex = 0;


    @Override
    public Optional<Manager> findById(ManagerId id) {
        return Optional.ofNullable(managers.get(id));
    }

    @Override
    public Collection<Manager> findAll() {
        return Collections.unmodifiableCollection(managers.values());
    }

    @Override
    public void delete(ManagerId id) throws RuntimeException {
        if (managers.remove(id) == null) {

            throw new RuntimeException("Temporary exception about manager");
        }
    }

    @Override
    public Manager update(Manager manager) throws RuntimeException {
        if (!managers.containsKey(manager.getId())) {
            throw new RuntimeException("Temporary exception about manager");
        }

        return managers.get(manager.getId());
    }

    @Override
    public Manager create(Manager manager) {
        ++lastIndex;
        return managers.put(new ManagerId(lastIndex), manager);
    }
}
