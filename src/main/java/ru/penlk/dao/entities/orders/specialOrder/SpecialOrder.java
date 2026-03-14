package ru.penlk.dao.entities.orders.specialOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.penlk.dao.entities.cars.CarId;
import ru.penlk.dao.entities.users.clients.ClientId;
import ru.penlk.dao.entities.users.managers.ManagerId;
import ru.penlk.dao.entities.valueObjects.Price;

@AllArgsConstructor
@Getter
@Setter
public class SpecialOrder {
    private SpecialOrderId id;
    private SpecialOrderState state;
    private ClientId clientId;
    private ManagerId managerId;
    private CarId carId;
    private Price price;
}
