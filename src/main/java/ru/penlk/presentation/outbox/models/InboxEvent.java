package ru.penlk.presentation.outbox.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import ru.penlk.dao.entities.outbox.OrderType;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class InboxEvent {
    private OrderType orderType;

    private Long sourceOrderId;

    private String event;

    private String explanation;
}
