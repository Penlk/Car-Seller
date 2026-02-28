package ru.penlk.dataAcessLayer.repositories.implementations;

import ru.penlk.dataAcessLayer.entities.orders.commonOrder.CommonOrder;
import ru.penlk.dataAcessLayer.entities.orders.commonOrder.CommonOrderId;
import ru.penlk.dataAcessLayer.repositories.interfaces.orders.commonOrder.CommonOrderNotFoundException;
import ru.penlk.dataAcessLayer.repositories.interfaces.orders.commonOrder.CommonOrderRepository;

import java.util.*;

public class InMemoryCommonOrderRepository implements CommonOrderRepository {
    Map<CommonOrderId, CommonOrder> orders = new HashMap<>();
    int lastIndex = 0;

    @Override
    public Collection<CommonOrder> findAll() {
        return Collections.unmodifiableCollection(orders.values());
    }

    @Override
    public Optional<CommonOrder> findById(CommonOrderId id) throws CommonOrderNotFoundException {
        return Optional.ofNullable(orders.get(id));
    }

    @Override
    public CommonOrder update(CommonOrder commonOrder) throws CommonOrderNotFoundException {
        if (!orders.containsKey(commonOrder.getId())) {
            throw new CommonOrderNotFoundException(commonOrder.getId());
        }

        return orders.get(commonOrder.getId());
    }

    @Override
    public void delete(CommonOrderId id) throws CommonOrderNotFoundException {
        if (orders.remove(id) == null) {

            throw new CommonOrderNotFoundException(id);
        }
    }

    @Override
    public CommonOrder create(CommonOrder node) {
        ++lastIndex;
        return orders.put(new CommonOrderId(lastIndex), node);
    }
}
