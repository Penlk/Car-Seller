package ru.penlk.dao.repositories.interfaces.nodes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.penlk.dao.entities.nodes.NodeId;

@Getter
@AllArgsConstructor
public class NodeAlreadyInException extends RuntimeException {
    private final NodeId nodeId;
}
