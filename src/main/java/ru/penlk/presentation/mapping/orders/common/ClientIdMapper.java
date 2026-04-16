package ru.penlk.presentation.mapping.orders.common;

import org.mapstruct.Mapper;
import ru.penlk.dao.entities.users.clients.Client;

@Mapper(componentModel = "spring")
public class ClientIdMapper {
    public Long clientToId(Client client) {
        return client.getId();
    }
}
