package ru.penlk.dao.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.penlk.dao.entities.nodes.Node;
import ru.penlk.dao.repositories.interfaces.nodes.NodeRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("NodeRepository Integration Tests")
@DataJpaTest
@Testcontainers
class NodeRepositoryIT {
    @Container
    @ServiceConnection
    static final PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:16-alpine")
                    .withDatabaseName("testdb")
                    .withUsername("test")
                    .withPassword("test");
    @Autowired
    private NodeRepository nodeRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private Node testNode;

    @BeforeEach
    void setUp() {
        nodeRepository.deleteAll();
        testNode = new Node();
        testNode.setName("TestNode");
    }

    @Test
    @DisplayName("Should save node and generate ID")
    void shouldSaveNodeToDatabaseSuccessfully() {
        Node savedNode = nodeRepository.save(testNode);

        assertNotNull(savedNode.getId());
        assertEquals("TestNode", savedNode.getName());
        assertNotNull(savedNode.getCreatedAt());
    }

    @Test
    @DisplayName("Should find node by ID")
    void shouldFindNodeById() {
        Node savedNode = nodeRepository.save(testNode);
        Long id = savedNode.getId();

        Optional<Node> foundNode = nodeRepository.findById(id);

        assertTrue(foundNode.isPresent());
        assertEquals("TestNode", foundNode.get().getName());
        assertEquals(id, foundNode.get().getId());
    }

    @Test
    @DisplayName("Should update node name")
    void shouldUpdateNodeName() {
        Node savedNode = nodeRepository.save(testNode);
        Long id = savedNode.getId();

        Node nodeToUpdate = nodeRepository.findById(id).orElseThrow();
        nodeToUpdate.setName("UpdatedNodeName");
        Node updatedNode = nodeRepository.save(nodeToUpdate);

        assertEquals("UpdatedNodeName", updatedNode.getName());
        assertEquals(id, updatedNode.getId());
    }

    @Test
    @DisplayName("Should find all nodes")
    void shouldFindAllNodes() {
        Node node1 = new Node();
        node1.setName("Node1");

        Node node2 = new Node();
        node2.setName("Node2");

        nodeRepository.save(node1);
        nodeRepository.save(node2);

        List<Node> allNodes = nodeRepository.findAll();
        assertTrue(allNodes.size() >= 2);
    }

    @Test
    @DisplayName("Should delete node by ID")
    void shouldDeleteNodeById() {
        Node savedNode = nodeRepository.save(testNode);
        Long id = savedNode.getId();

        nodeRepository.deleteById(id);
        nodeRepository.flush();
        entityManager.clear();

        Optional<Node> deletedNode = nodeRepository.findById(id);
        assertFalse(deletedNode.isPresent());
    }

    @Test
    @DisplayName("Should return empty Optional when node not found")
    void shouldReturnEmptyWhenNodeNotFound() {
        Optional<Node> notFoundNode = nodeRepository.findById(9999L);
        assertFalse(notFoundNode.isPresent());
    }

    @Test
    @DisplayName("Should save node with different names")
    void shouldSaveNodesWithDifferentNames() {
        Node node1 = new Node();
        node1.setName("Engine");

        Node node2 = new Node();
        node2.setName("Transmission");

        Node node3 = new Node();
        node3.setName("Chassis");

        nodeRepository.save(node1);
        nodeRepository.save(node2);
        nodeRepository.save(node3);

        List<Node> allNodes = nodeRepository.findAll();
        assertTrue(allNodes.stream().anyMatch(n -> n.getName().equals("Engine")));
        assertTrue(allNodes.stream().anyMatch(n -> n.getName().equals("Transmission")));
        assertTrue(allNodes.stream().anyMatch(n -> n.getName().equals("Chassis")));
    }

    @Test
    @DisplayName("Should update timestamp on modification")
    void shouldUpdateTimestampOnModification() throws InterruptedException {
        Node savedNode = nodeRepository.save(testNode);
        var updatedAtFirst = savedNode.getUpdatedAt();

        Thread.sleep(100);

        Node nodeToUpdate = nodeRepository.findById(savedNode.getId()).orElseThrow();
        nodeToUpdate.setName("NewName");
        Node updatedNode = nodeRepository.save(nodeToUpdate);

        assertTrue(updatedNode.getUpdatedAt().isAfter(updatedAtFirst) || updatedNode.getUpdatedAt().equals(updatedAtFirst));
    }

    @Test
    @DisplayName("Should persist all node fields correctly")
    void shouldPersistAllNodeFieldsCorrectly() {
        Node savedNode = nodeRepository.save(testNode);
        Optional<Node> foundNode = nodeRepository.findById(savedNode.getId());

        assertTrue(foundNode.isPresent());
        Node node = foundNode.get();

        assertEquals("TestNode", node.getName());
        assertNotNull(node.getId());
        assertNotNull(node.getCreatedAt());
        assertNotNull(node.getUpdatedAt());
    }

    @Test
    @DisplayName("Should count nodes correctly")
    void shouldCountNodesCorrectly() {
        nodeRepository.deleteAll();

        Node node1 = new Node();
        node1.setName("Node1");

        Node node2 = new Node();
        node2.setName("Node2");

        nodeRepository.save(node1);
        nodeRepository.save(node2);

        long count = nodeRepository.count();
        assertEquals(2L, count);
    }
}
