package ru.penlk.businessLayer.contracts.carParts.models;

import ru.penlk.dataAcessLayer.entities.carParts.CarPart;

public record CarPartDto(long id,
                         String namePart,
                         long nodeId) {

    public static CarPartDto MapToDto(CarPart carPart) {
        return new CarPartDto(carPart.getId().id(), carPart.getNamePart(), carPart.getNodeId().id());
    }
}
