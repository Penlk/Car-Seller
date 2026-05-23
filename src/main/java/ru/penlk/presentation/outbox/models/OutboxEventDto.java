package ru.penlk.presentation.outbox.models;

import ru.penlk.dao.entities.outbox.OrderType;

import java.util.Set;

public record OutboxEventDto(
    String name,
    OrderType orderType,
    Long orderSourceId,
    Long carSourceId,
    Set<Long> carPartSourceIds
) { }
