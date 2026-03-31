package ru.penlk.dao.entities.orders.special;


import lombok.AllArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
public class SpecialAllowedPartId implements Serializable {
    private Long carPart;
    private Long car;
}
