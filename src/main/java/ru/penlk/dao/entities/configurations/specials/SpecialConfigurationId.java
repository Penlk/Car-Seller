package ru.penlk.dao.entities.configurations.specials;

import lombok.AllArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
public class SpecialConfigurationId implements Serializable {
    private Long configurator;
    private Long carPart;
}
