package ru.penlk.dataAcessLayer.repositories.implementations;

import ru.penlk.dataAcessLayer.entities.orders.specialOrder.SpecialOrder;
import ru.penlk.dataAcessLayer.entities.orders.specialOrder.SpecialOrderId;
import ru.penlk.dataAcessLayer.repositories.interfaces.orders.specialOrders.SpecialOrderNotFoundException;
import ru.penlk.dataAcessLayer.repositories.interfaces.orders.specialOrders.SpecialOrderRepository;

import java.util.*;

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
