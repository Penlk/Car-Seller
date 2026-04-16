package ru.penlk.presentation.mapping.vo;

import org.mapstruct.Mapper;
import ru.penlk.dao.entities.vo.EnginePower;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public class EnginePowerMapper {
    public EnginePower mapEnginePower(BigDecimal enginePower) {
        return new EnginePower(enginePower);
    }

    public BigDecimal mapRaw(EnginePower enginePower) {
        return enginePower.engine_power();
    }
}
