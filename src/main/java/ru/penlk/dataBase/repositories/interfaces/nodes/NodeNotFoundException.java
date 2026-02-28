package ru.penlk.dataBase.repositories.interfaces.nodes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.penlk.dataBase.entities.nodes.NodeId;

@Getter
@AllArgsConstructor
public class NodeNotFoundException extends RuntimeException {
    private final NodeId nodeId;
}
