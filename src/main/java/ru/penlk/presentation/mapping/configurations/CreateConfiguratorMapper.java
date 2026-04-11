package ru.penlk.presentation.mapping.configurations;

import org.mapstruct.Mapper;
import ru.penlk.dao.entities.configurations.specials.Configurator;
import ru.penlk.presentation.configurations.models.CreateConfiguratorDto;
import ru.penlk.presentation.mapping.orders.common.CarIdMapper;

@Mapper(componentModel = "spring", uses = {
        CarIdMapper.class
})
public interface CreateConfiguratorMapper {
    Configurator CreateDtoToConfigurator(CreateConfiguratorDto dto);
}
