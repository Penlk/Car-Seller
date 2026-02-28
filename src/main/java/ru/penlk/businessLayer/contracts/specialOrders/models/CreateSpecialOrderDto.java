package ru.penlk.businessLayer.contracts.specialOrders.models;

import java.util.Collection;

public record CreateSpecialOrderDto(String state,
                                    long clientId,
                                    long managerId,
                                    long carId,
                                    Collection<SpecialOrderDto> specialOrders) {

}
