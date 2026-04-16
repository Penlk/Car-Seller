package ru.penlk.presentation.mapping.orders.common;

import org.mapstruct.Mapper;
import ru.penlk.dao.entities.users.managers.Manager;

@Mapper(componentModel = "spring")
public class ManagerIdMapper {
    public Long managerToId(Manager manager) {
        if (manager == null) {
            return null;
        }

        return manager.getId();
    }
}
