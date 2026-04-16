package ru.penlk.business.implementations.fk;

import lombok.AllArgsConstructor;
import ru.penlk.business.contracts.cars.fk.RequireNodeProvider;
import ru.penlk.dao.entities.BaseEntity;
import ru.penlk.dao.entities.cars.Car;
import ru.penlk.dao.entities.nodes.Node;
import ru.penlk.dao.entities.nodes.RequireNode;
import ru.penlk.dao.repositories.interfaces.nodes.NodeRepository;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
public class RequireNodeProviderImpl implements RequireNodeProvider {
    private final Set<Long> nodeIds;

    @Override
    public Set<RequireNode> getRequireNodes(Car car, NodeRepository nodeRepository) {
        Set<Node> actuallyNodes = new HashSet<>(nodeRepository.findAllById(nodeIds));

        Set<Long> actuallyNodeIds = new HashSet<>(actuallyNodes.stream().map(BaseEntity::getId).toList());

        nodeIds.forEach(id -> {
            if (!actuallyNodeIds.contains(id)) {
                throw new IllegalArgumentException(String.format("Node with id %s not found", id));
            }
        });

        return new HashSet<>(actuallyNodes.stream().map(x -> new RequireNode(x, car)).toList());
    }
}
