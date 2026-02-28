package ru.penlk.dataAcessLayer.repositories.implementations;

import ru.penlk.dataAcessLayer.entities.nodes.Node;
import ru.penlk.dataAcessLayer.entities.nodes.NodeId;
import ru.penlk.dataAcessLayer.repositories.interfaces.nodes.NodeAlreadyInException;
import ru.penlk.dataAcessLayer.repositories.interfaces.nodes.NodeNotFoundException;
import ru.penlk.dataAcessLayer.repositories.interfaces.nodes.NodeRepository;

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
