package ru.penlk.dataAcessLayer.repositories.interfaces.nodes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.penlk.dataAcessLayer.entities.nodes.NodeId;

@Getter
@AllArgsConstructor
public class NodeNotFoundException extends RuntimeException {
    private final NodeId nodeId;
}
