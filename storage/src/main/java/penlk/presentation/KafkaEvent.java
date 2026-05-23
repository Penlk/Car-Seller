package penlk.presentation;

import penlk.dao.entities.orders.OrderType;

import java.util.Set;

public record KafkaEvent(
    String name,
    OrderType orderType,
    Long orderSourceId,
    Long carSourceId,
    Set<Long> carPartSourceIds
) { }
