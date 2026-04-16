package ru.penlk.presentation.mapping.configurations;

import org.mapstruct.Mapper;
import ru.penlk.dao.entities.configurations.specials.SpecialConfiguration;

@Mapper(componentModel = "spring")
public class SpecialConfigurationIdMapper {
    public Long specialConfigurationToId(SpecialConfiguration configuration) {
        return configuration.getSpecialAllowedPart().getId();
    }
}
