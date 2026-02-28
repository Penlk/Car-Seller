package ru.penlk.dataAcessLayer.repositories.interfaces.nodes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.penlk.dataAcessLayer.entities.nodes.NodeId;

@Getter
@AllArgsConstructor
public class NodeAlreadyInException extends RuntimeException {
    private final NodeId nodeId;
}
