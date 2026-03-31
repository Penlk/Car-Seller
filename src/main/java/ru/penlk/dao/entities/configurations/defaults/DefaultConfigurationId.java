package ru.penlk.dao.entities.configurations.defaults;

import lombok.AllArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
public class DefaultConfigurationId implements Serializable {
    private Long carPart;
    private Long car;
}
