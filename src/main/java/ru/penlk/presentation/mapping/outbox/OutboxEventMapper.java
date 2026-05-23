package ru.penlk.presentation.mapping.outbox;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.penlk.business.implementations.orders.strategies.ManagerSelectionStrategy;
import ru.penlk.dao.entities.outbox.OutboxEvent;
import ru.penlk.presentation.mapping.configurations.ConfiguratorCarPartIdMapper;
import ru.penlk.presentation.outbox.models.OutboxEventDto;

@Mapper(componentModel = "spring", uses = CarPartIdMapper.class)
public interface OutboxEventMapper {
    @Mapping(source = "carPartSources", target = "carPartSourceIds")
    OutboxEventDto map(OutboxEvent event);
}
