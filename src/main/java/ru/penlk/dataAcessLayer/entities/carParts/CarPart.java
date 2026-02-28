package ru.penlk.dataAcessLayer.entities.carParts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.penlk.dataAcessLayer.entities.nodes.NodeId;

@AllArgsConstructor
@Getter
@Setter
public class CarPart {
    private CarPartId id;
    private String namePart;
    private NodeId nodeId;
}
