package ru.penlk.dataAcessLayer.entities.orders.specialOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.penlk.dataAcessLayer.entities.cars.CarId;
import ru.penlk.dataAcessLayer.entities.users.clients.ClientId;
import ru.penlk.dataAcessLayer.entities.users.managers.ManagerId;

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
