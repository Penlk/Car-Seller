package ru.penlk.business.implementations.orders.strategies;

import org.springframework.stereotype.Component;
import ru.penlk.dao.entities.users.managers.Manager;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Component
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
