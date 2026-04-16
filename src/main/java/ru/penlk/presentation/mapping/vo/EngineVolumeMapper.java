package ru.penlk.presentation.mapping.vo;

import org.mapstruct.Mapper;
import ru.penlk.dao.entities.vo.EngineVolume;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public class EngineVolumeMapper {
    public EngineVolume mapEngineVolume(BigDecimal raw) {
        return new EngineVolume(raw);
    }

    public BigDecimal mapRaw(EngineVolume engineVolume) {
        return engineVolume.engine_volume();
    }
}
