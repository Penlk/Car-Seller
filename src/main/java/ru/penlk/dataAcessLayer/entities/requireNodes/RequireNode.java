package ru.penlk.dataAcessLayer.entities.requireNodes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.penlk.dataAcessLayer.entities.cars.CarId;
import ru.penlk.dataAcessLayer.entities.nodes.NodeId;

@AllArgsConstructor
@Getter
@Setter
public class RequireNode {
    private NodeId nodeId;
    private CarId carId;
}
