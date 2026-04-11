package ru.penlk.business.contracts.nodes;

import ru.penlk.business.contracts.ServiceException;
import ru.penlk.dao.entities.nodes.Node;

public interface NodeService {
    Node create(Node request);

    Node find(Long nodeId) throws ServiceException;

    Node update(Node request) throws ServiceException;

    void delete(Long nodeId) throws ServiceException;
}
