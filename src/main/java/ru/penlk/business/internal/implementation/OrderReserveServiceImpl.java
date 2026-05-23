package ru.penlk.business.internal.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.penlk.business.implementations.orders.states.common.CommonOrderCore;
import ru.penlk.business.implementations.orders.states.common.CommonOrderFacade;
import ru.penlk.business.implementations.orders.states.common.CommonOrderStateHandler;
import ru.penlk.business.implementations.orders.states.mappers.CommonStateMapper;
import ru.penlk.business.implementations.orders.states.mappers.SpecialStateMapper;
import ru.penlk.business.implementations.orders.states.special.SpecialOrderCore;
import ru.penlk.business.implementations.orders.states.special.SpecialOrderFacade;
import ru.penlk.business.implementations.orders.states.special.SpecialOrderStateHandler;
import ru.penlk.business.internal.OrderReserveService;
import ru.penlk.dao.entities.orders.common.CommonOrder;
import ru.penlk.dao.entities.orders.special.SpecialOrder;
import ru.penlk.dao.entities.outbox.OrderType;
import ru.penlk.dao.repositories.interfaces.orders.common.CommonOrderRepository;
import ru.penlk.dao.repositories.interfaces.orders.special.SpecialOrderRepository;

@Service
@AllArgsConstructor
@Transactional
public class OrderReserveServiceImpl implements OrderReserveService {
    private final CommonOrderRepository commonOrderRepository;
    private final SpecialOrderRepository specialOrderRepository;
    private final CommonStateMapper commonStateMapper;
    private final SpecialStateMapper specialStateMapper;

    @Override
    public void reserve(Long orderId, OrderType orderType) throws RuntimeException {
        switch (orderType) {
            case OrderType.COMMON -> {
                CommonOrder commonOrder = commonOrderRepository.findById(orderId).orElseThrow(
                        () -> new RuntimeException(String.format("Cannot find common order with id %s", orderId))
                );

                CommonOrderStateHandler handler = commonStateMapper.map(commonOrder.getState());
                CommonOrderFacade facade = new CommonOrderFacade(new CommonOrderCore(commonOrder, handler));
                if (facade.tryReserve() == Boolean.FALSE) {
                    throw new RuntimeException(String.format("Cannot reserve common order with id %s", orderId));
                }
            }
            case OrderType.SPECIAL -> {
                SpecialOrder specialOrder = specialOrderRepository.findById(orderId).orElseThrow(
                        () -> new RuntimeException(String.format("Cannot find special order with id %s", orderId))
                );

                SpecialOrderStateHandler handler = specialStateMapper.map(specialOrder.getState());
                SpecialOrderFacade facade = new SpecialOrderFacade(new SpecialOrderCore(specialOrder, handler));
                if (facade.tryReserve() == Boolean.FALSE) {
                    throw new RuntimeException(String.format("Cannot reserve special order with id %s", orderId));
                }
            }
        }
    }
}
