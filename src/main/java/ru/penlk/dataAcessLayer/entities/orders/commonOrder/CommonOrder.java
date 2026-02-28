package ru.penlk.dataAcessLayer.entities.orders.commonOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.penlk.dataAcessLayer.entities.cars.CarId;
import ru.penlk.dataAcessLayer.entities.users.clients.ClientId;
import ru.penlk.dataAcessLayer.entities.users.managers.ManagerId;

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
