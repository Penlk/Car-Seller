package ru.penlk.businessLayer.implementations.orders;

import ru.penlk.dataAcessLayer.entities.users.managers.Manager;
import ru.penlk.dataAcessLayer.repositories.interfaces.users.managers.ManagerRepository;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class DefaultManagerSelection implements ManagerSelectionStrategy {
    @Override
    public Optional<Manager> findManager(Collection<Manager> managers) {
        int randomIndex = ThreadLocalRandom.current().nextInt(managers.size());

        Iterator<Manager> managerIterator = managers.stream().iterator();

        for (int i = 0; i < randomIndex; i++) {
            managerIterator.next();
        }

        return Optional.ofNullable(managerIterator.next());
    }
}
