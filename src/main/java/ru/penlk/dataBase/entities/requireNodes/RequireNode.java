package ru.penlk.dataBase.entities.requireNodes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.penlk.dataBase.entities.cars.CarId;

@AllArgsConstructor
@Getter
@Setter
public class RequireNode {
    private String nameNode;
    private CarId carId;
}
