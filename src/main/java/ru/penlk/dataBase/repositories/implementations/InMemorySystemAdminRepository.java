package ru.penlk.dataBase.repositories.implementations;



import ru.penlk.dataBase.entities.users.systemAdmins.SystemAdmin;
import ru.penlk.dataBase.entities.users.systemAdmins.SystemAdminId;
import ru.penlk.dataBase.repositories.interfaces.users.systemAdmins.SystemAdminRepository;

import java.util.*;

public class InMemorySystemAdminRepository implements SystemAdminRepository {
    Map<SystemAdminId, SystemAdmin> systemAdmins = new HashMap<>();
    int lastIndex = 0;


    @Override
    public Optional<SystemAdmin> findById(SystemAdminId id) {
        return Optional.ofNullable(systemAdmins.get(id));
    }

    @Override
    public Collection<SystemAdmin> findAll() {
        return Collections.unmodifiableCollection(systemAdmins.values());
    }

    @Override
    public void delete(SystemAdminId id) throws RuntimeException {
        if (systemAdmins.remove(id) == null) {

            throw new RuntimeException("Temporary exception about systemAdmin");
        }
    }

    @Override
    public SystemAdmin update(SystemAdmin systemAdmin) throws RuntimeException {
        if (!systemAdmins.containsKey(systemAdmin.getId())) {
            throw new RuntimeException("Temporary exception about systemAdmin");
        }

        return systemAdmins.get(systemAdmin.getId());
    }

    @Override
    public SystemAdmin create(SystemAdmin systemAdmin) {
        ++lastIndex;
        return systemAdmins.put(new SystemAdminId(lastIndex), systemAdmin);
    }
}
