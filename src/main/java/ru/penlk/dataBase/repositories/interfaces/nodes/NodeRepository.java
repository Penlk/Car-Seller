package ru.penlk.dataBase.repositories.interfaces.nodes;

import ru.penlk.dataBase.entities.nodes.Node;
import ru.penlk.dataBase.entities.nodes.NodeId;

import java.util.Collection;
import java.util.Optional;

public interface NodeRepository {
    Collection<Node> findAll();
    Optional<Node> findById(NodeId id);
    Node update(Node node) throws NodeNotFoundException;
    void delete(NodeId nodeId) throws NodeNotFoundException;
    Node create(Node node) throws NodeAlreadyInException;
}
