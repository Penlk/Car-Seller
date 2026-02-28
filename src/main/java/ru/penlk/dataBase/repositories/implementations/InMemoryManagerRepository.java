package ru.penlk.dataBase.repositories.implementations;


import ru.penlk.dataBase.entities.users.managers.Manager;
import ru.penlk.dataBase.entities.users.managers.ManagerId;
import ru.penlk.dataBase.repositories.interfaces.users.managers.ManagerRepository;

import java.util.*;

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
