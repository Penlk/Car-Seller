package ru.penlk.dataBase.entities.orders.commonOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.penlk.dataBase.entities.cars.CarId;
import ru.penlk.dataBase.entities.users.clients.ClientId;
import ru.penlk.dataBase.entities.users.managers.ManagerId;

@AllArgsConstructor
@Getter
@Setter
public class CommonOrder {
    private CommonOrderId id;
    private CommonOrderState state;
    private ClientId clientId;
    private ManagerId managerId;
    private CarId carId;
}
