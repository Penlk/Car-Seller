package ru.penlk.business.contracts.cars.fk;

import ru.penlk.dao.entities.cars.Car;
import ru.penlk.dao.entities.nodes.RequireNode;
import ru.penlk.dao.repositories.interfaces.nodes.NodeRepository;

import java.util.Set;

public interface RequireNodeProvider {
    Set<RequireNode> getRequireNodes(Car car, NodeRepository nodeRepository);
}
