package ru.penlk.dataBase.entities.orders.specialOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.penlk.dataBase.entities.cars.CarId;
import ru.penlk.dataBase.entities.users.clients.ClientId;
import ru.penlk.dataBase.entities.users.managers.ManagerId;

@AllArgsConstructor
@Getter
@Setter
public class SpecialOrder {
    private SpecialOrderId id;
    private SpecialOrderState state;
    private ClientId clientId;
    private ManagerId managerId;
    private CarId carId;
}
