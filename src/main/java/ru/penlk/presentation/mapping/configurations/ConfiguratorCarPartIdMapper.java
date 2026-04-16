package ru.penlk.presentation.mapping.configurations;

import org.mapstruct.Mapper;
import ru.penlk.dao.entities.configurations.specials.ConfiguratorCarPart;

@Mapper(componentModel = "spring")
public class ConfiguratorCarPartIdMapper {
    public Long configuratorCarPartToId(ConfiguratorCarPart carPart) {
        return carPart.getCarPart().getId();
    }
}
