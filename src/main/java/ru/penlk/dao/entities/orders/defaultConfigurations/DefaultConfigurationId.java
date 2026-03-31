package ru.penlk.dao.entities.orders.defaultConfigurations;

import lombok.AllArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
public class DefaultConfigurationId implements Serializable {
    private Long carPart;
    private Long car;
}
