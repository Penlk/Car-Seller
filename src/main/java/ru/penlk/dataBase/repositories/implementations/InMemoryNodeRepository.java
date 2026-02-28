package ru.penlk.dataBase.repositories.implementations;

import ru.penlk.dataBase.entities.carParts.CarPart;
import ru.penlk.dataBase.entities.carParts.CarPartId;
import ru.penlk.dataBase.entities.nodes.Node;
import ru.penlk.dataBase.entities.nodes.NodeId;
import ru.penlk.dataBase.repositories.interfaces.carParts.CarPartNotFoundException;
import ru.penlk.dataBase.repositories.interfaces.carParts.CarPartRepository;
import ru.penlk.dataBase.repositories.interfaces.nodes.NodeAlreadyInException;
import ru.penlk.dataBase.repositories.interfaces.nodes.NodeNotFoundException;
import ru.penlk.dataBase.repositories.interfaces.nodes.NodeRepository;

import java.util.*;

public class InMemoryNodeRepository implements NodeRepository {
    Map<NodeId, Node> nodes = new HashMap<>();
    int lastIndex = 0;

    @Override
    public Collection<Node> findAll() {
        return Collections.unmodifiableCollection(nodes.values());
    }

    @Override
    public Optional<Node> findById(NodeId id) {
        return Optional.ofNullable(nodes.get(id));
    }

    @Override
    public Node update(Node node) throws NodeNotFoundException {
        if (!nodes.containsKey(node.getId())) {
            throw new NodeNotFoundException(node.getId());
        }

        return nodes.get(node.getId());
    }

    @Override
    public void delete(NodeId nodeId) throws NodeNotFoundException {
        if (nodes.remove(nodeId) == null) {

            throw new NodeNotFoundException(nodeId);
        }
    }

    @Override
    public Node create(Node node) throws NodeAlreadyInException {
        ++lastIndex;
        return nodes.put(new NodeId(lastIndex), node);
    }
}
