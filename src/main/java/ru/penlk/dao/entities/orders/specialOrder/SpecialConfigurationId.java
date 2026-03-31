package ru.penlk.dao.entities.orders.specialOrder;

import lombok.AllArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
public class SpecialConfigurationId implements Serializable {
    private Long specialOrderId;
    private Long carPartId;
}
