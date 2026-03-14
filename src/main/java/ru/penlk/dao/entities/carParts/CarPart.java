package ru.penlk.dao.entities.carParts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.penlk.dao.entities.nodes.NodeId;

@AllArgsConstructor
@Getter
@Setter
public class CarPart {
    private CarPartId id;
    private String namePart;
    private NodeId nodeId;
}
