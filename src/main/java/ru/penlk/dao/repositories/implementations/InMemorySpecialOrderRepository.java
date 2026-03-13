package ru.penlk.dao.repositories.implementations;

import ru.penlk.dao.entities.orders.specialOrder.SpecialOrder;
import ru.penlk.dao.entities.orders.specialOrder.SpecialOrderId;
import ru.penlk.dao.repositories.interfaces.orders.special.SpecialOrderNotFoundException;
import ru.penlk.dao.repositories.interfaces.orders.special.SpecialOrderRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemorySpecialOrderRepository implements SpecialOrderRepository {
    Map<SpecialOrderId, SpecialOrder> orders = new HashMap<>();
    int lastIndex = 0;

    @Override
    public Collection<SpecialOrder> findAll() {
        return Collections.unmodifiableCollection(orders.values());
    }

    @Override
    public Optional<SpecialOrder> findById(SpecialOrderId id) throws SpecialOrderNotFoundException {
        return Optional.ofNullable(orders.get(id));
    }

    @Override
    public SpecialOrder update(SpecialOrder commonOrder) throws SpecialOrderNotFoundException {
        if (!orders.containsKey(commonOrder.getId())) {
            throw new SpecialOrderNotFoundException(commonOrder.getId());
        }

        return orders.get(commonOrder.getId());
    }

    @Override
    public void delete(SpecialOrderId id) throws SpecialOrderNotFoundException {
        if (orders.remove(id) == null) {

            throw new SpecialOrderNotFoundException(id);
        }
    }

    @Override
    public SpecialOrder create(SpecialOrder node) {
        ++lastIndex;
        return orders.put(new SpecialOrderId(lastIndex), node);
    }
}
