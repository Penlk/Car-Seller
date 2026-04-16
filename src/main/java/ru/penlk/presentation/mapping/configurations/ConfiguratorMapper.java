package ru.penlk.presentation.mapping.configurations;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.penlk.dao.entities.configurations.specials.Configurator;
import ru.penlk.presentation.configurations.models.ConfiguratorDto;
import ru.penlk.presentation.mapping.orders.common.CarIdMapper;

@Mapper(componentModel = "spring", uses = {
        CarIdMapper.class,
        ConfiguratorCarPartIdMapper.class,
        SpecialConfigurationIdMapper.class
})
public interface ConfiguratorMapper {
    @Mapping(source = "car", target = "carId")
    @Mapping(source = "specialConfigurations", target = "specialConfigurationIds")
    @Mapping(source = "carParts", target = "carPartIds")
    ConfiguratorDto ConfiguratorToDto(Configurator dto);

    Configurator DtoToConfigurator(ConfiguratorDto dto);
}
