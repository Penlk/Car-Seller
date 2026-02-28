package ru.penlk.dataBase.entities.requireNodes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.penlk.dataBase.entities.cars.CarId;
import ru.penlk.dataBase.entities.nodes.NodeId;

@AllArgsConstructor
@Getter
@Setter
public class RequireNode {
    private NodeId nodeId;
    private CarId carId;
}
