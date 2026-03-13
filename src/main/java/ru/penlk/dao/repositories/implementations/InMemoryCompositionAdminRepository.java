package ru.penlk.dao.repositories.implementations;


import ru.penlk.dao.entities.users.compositionAdmins.CompositionAdmin;
import ru.penlk.dao.entities.users.compositionAdmins.CompositionAdminId;
import ru.penlk.dao.repositories.interfaces.users.compositionAdmins.CompositionAdminRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryCompositionAdminRepository implements CompositionAdminRepository {
    Map<CompositionAdminId, CompositionAdmin> compositionAdmins = new HashMap<>();
    int lastIndex = 0;


    @Override
    public Optional<CompositionAdmin> findById(CompositionAdminId id) {
        return Optional.ofNullable(compositionAdmins.get(id));
    }

    @Override
    public Collection<CompositionAdmin> findAll() {
        return Collections.unmodifiableCollection(compositionAdmins.values());
    }

    @Override
    public void delete(CompositionAdminId id) throws RuntimeException {
        if (compositionAdmins.remove(id) == null) {

            throw new RuntimeException("Temporary exception about compositionAdmin");
        }
    }

    @Override
    public CompositionAdmin update(CompositionAdmin compositionAdmin) throws RuntimeException {
        if (!compositionAdmins.containsKey(compositionAdmin.getId())) {
            throw new RuntimeException("Temporary exception about compositionAdmin");
        }

        return compositionAdmins.get(compositionAdmin.getId());
    }

    @Override
    public CompositionAdmin create(CompositionAdmin compositionAdmin) {
        ++lastIndex;
        return compositionAdmins.put(new CompositionAdminId(lastIndex), compositionAdmin);
    }
}
