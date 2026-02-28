package ru.penlk.dataBase.entities.carParts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.penlk.dataBase.entities.nodes.NodeId;

@AllArgsConstructor
@Getter
@Setter
public class CarPart {
    private CarPartId id;
    private String namePart;
    private NodeId nodeId;
}
