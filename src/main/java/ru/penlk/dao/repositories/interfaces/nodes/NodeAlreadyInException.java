package ru.penlk.dao.repositories.interfaces.nodes;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NodeAlreadyInException extends RuntimeException {
    private final NodeId nodeId;
}
