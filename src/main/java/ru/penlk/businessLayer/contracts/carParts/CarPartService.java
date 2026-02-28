package ru.penlk.businessLayer.contracts.carParts;

public interface CarPartService {
    CreateCarPart.Response create(CreateCarPart.Request request);

    ReadCarPart.Response read(ReadCarPart.Request request);

    UpdateCarPart.Response update(UpdateCarPart.Request request);

    DeleteCarPart.Response delete(DeleteCarPart.Request request);
}
