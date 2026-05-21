package ru.penlk.presentation.outbox.models;

import lombok.Getter;

@Getter
public class InboxEvent {
    private Long sourceOrderId;

    private String event;

    private String explanation;
}
