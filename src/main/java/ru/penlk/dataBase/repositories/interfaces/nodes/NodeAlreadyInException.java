package ru.penlk.dataBase.repositories.interfaces.nodes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.penlk.dataBase.entities.nodes.NodeId;

@Getter
@AllArgsConstructor
public class NodeAlreadyInException extends RuntimeException {
    private final NodeId nodeId;
}
