package ru.penlk.presentation.mapping.outbox;

import org.mapstruct.Mapper;
import ru.penlk.dao.entities.outbox.OutboxEvent;
import ru.penlk.presentation.outbox.models.OutboxEventDto;

@Mapper(componentModel = "spring")
public interface OutboxEventMapper {
    OutboxEventDto map(OutboxEvent event);
}
