package ru.penlk.dao.entities.requireNodes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.penlk.dao.entities.cars.CarId;
import ru.penlk.dao.entities.nodes.NodeId;

@AllArgsConstructor
@Getter
@Setter
public class RequireNode {
    private NodeId nodeId;
    private CarId carId;
}
