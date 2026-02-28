package ru.penlk.businessLayer.contracts.cars;

import ru.penlk.businessLayer.contracts.cars.operations.CreateCar;
import ru.penlk.businessLayer.contracts.cars.operations.DeleteCar;
import ru.penlk.businessLayer.contracts.cars.operations.ReadCar;
import ru.penlk.businessLayer.contracts.cars.operations.UpdateCar;

public interface CarService {
    CreateCar.Response create(CreateCar.Request request);

    ReadCar.Response read(ReadCar.Request request);

    UpdateCar.Response update(UpdateCar.Request request);

    DeleteCar.Response delete(DeleteCar.Request request);
}
