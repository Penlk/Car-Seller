package ru.penlk.business.implementations.orders.strategies;

import org.springframework.stereotype.Component;
import ru.penlk.dao.entities.users.managers.Manager;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class DefaultManagerSelection implements ManagerSelectionStrategy {
    @Override
    public Optional<Manager> findManager(List<Manager> managers) {
        int randomIndex = ThreadLocalRandom.current().nextInt(managers.size());

        return Optional.ofNullable(managers.get(randomIndex));
    }
}
