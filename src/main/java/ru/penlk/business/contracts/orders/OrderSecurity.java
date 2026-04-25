package ru.penlk.business.contracts.orders;

public interface OrderSecurity {
    Boolean isOwnerCommonOrder(Long orderId);

    Boolean isOwnerSpecialOrder(Long orderId);
}
