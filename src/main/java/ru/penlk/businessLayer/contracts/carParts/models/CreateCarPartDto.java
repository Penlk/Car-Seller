package ru.penlk.businessLayer.contracts.carParts.models;

import java.math.BigDecimal;

public record CreateCarPartDto(String namePart,
                               long nodeId) {

}
