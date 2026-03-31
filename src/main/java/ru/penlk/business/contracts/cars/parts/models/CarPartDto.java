package ru.penlk.business.contracts.cars.parts.models;

import ru.penlk.dao.entities.cars.CarPart;

public record CarPartDto(long id,
                         String namePart,
                         long nodeId) {

    public static CarPartDto MapToDto(CarPart carPart) {
        return new CarPartDto(carPart.getId().id(), carPart.getNamePart(), carPart.getNode().id());
    }
}
