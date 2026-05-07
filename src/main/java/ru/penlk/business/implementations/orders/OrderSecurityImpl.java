package ru.penlk.business.implementations.orders;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.penlk.business.contracts.orders.OrderSecurity;
import ru.penlk.dao.entities.orders.common.CommonOrder;
import ru.penlk.dao.entities.orders.special.SpecialOrder;
import ru.penlk.dao.repositories.interfaces.orders.common.CommonOrderRepository;
import ru.penlk.dao.repositories.interfaces.orders.special.SpecialOrderRepository;

import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderSecurityImpl implements OrderSecurity {
    private final CommonOrderRepository commonOrderRepository;
    private final SpecialOrderRepository specialOrderRepository;

    @Override
    public Boolean isOwnerCommonOrder(Long orderId) {
        Optional<CommonOrder> optionalOrder = commonOrderRepository.findById(orderId);

        if (optionalOrder.isEmpty()) {
            return false;
        }

        CommonOrder order = optionalOrder.get();

        String userId = getUserId();

        return order.getOwnerId().equals(getUserId());
    }

    @Override
    public Boolean isOwnerSpecialOrder(Long orderId) {
        Optional<SpecialOrder> optionalOrder = specialOrderRepository.findById(orderId);

        if (optionalOrder.isEmpty()) {
            return false;
        }

        SpecialOrder order = optionalOrder.get();

        return order.getOwnerId().equals(getUserId());
    }

    private String getUserId() {
        return Objects.requireNonNull(SecurityContextHolder.getContext()
                        .getAuthentication())
                .getName();
    }
}
