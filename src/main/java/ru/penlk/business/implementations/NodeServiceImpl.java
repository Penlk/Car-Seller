package ru.penlk.business.implementations;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.penlk.business.contracts.ServiceException;
import ru.penlk.business.contracts.nodes.NodeService;
import ru.penlk.dao.entities.nodes.Node;
import ru.penlk.dao.repositories.interfaces.nodes.NodeRepository;

@AllArgsConstructor
@Service
@Transactional
public class NodeServiceImpl implements NodeService {
    private final NodeRepository nodeRepository;

    @Override
    public Node create(Node request) {
        return nodeRepository.save(request);
    }

    @Override
    public Node read(Long nodeId) throws ServiceException {
        return nodeRepository.findById(nodeId)
                .orElseThrow(() -> new ServiceException("Node not found"));
    }

    @Override
    public Node update(Node request) throws ServiceException {
        Node node = nodeRepository.findById(request.getId())
                .orElseThrow(() -> new ServiceException("Node not found"));

        node.setName(request.getName());

        return nodeRepository.save(node);
    }

    @Override
    public void delete(Long nodeId) throws ServiceException {
        Node node = nodeRepository.findById(nodeId).orElseThrow(() -> new ServiceException("Node not found"));

        nodeRepository.delete(node);
    }
}
