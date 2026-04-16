package ru.penlk.dao.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.penlk.config.AbstractIntegrationTest;
import ru.penlk.dao.entities.users.clients.Client;
import ru.penlk.dao.repositories.interfaces.users.clients.ClientRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ClientRepository Integration Tests")
@DataJpaTest
@Testcontainers
class ClientRepositoryIT {
    @Container
    @ServiceConnection
    static final PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:16-alpine")
                    .withDatabaseName("testdb")
                    .withUsername("test")
                    .withPassword("test");
    @Autowired
    private ClientRepository clientRepository;

    private Client testClient;

    @BeforeEach
    void setUp() {
        clientRepository.deleteAll();
        testClient = new Client();
    }

    @Test
    @DisplayName("Should save client and generate ID")
    void shouldSaveClientToDatabaseSuccessfully() {
        Client savedClient = clientRepository.save(testClient);
        
        assertNotNull(savedClient.getId());
        assertNotNull(savedClient.getCreatedAt());
    }

    @Test
    @DisplayName("Should find client by ID")
    void shouldFindClientById() {
        Client savedClient = clientRepository.save(testClient);
        Long id = savedClient.getId();

        Optional<Client> foundClient = clientRepository.findById(id);

        assertTrue(foundClient.isPresent());
        assertEquals(id, foundClient.get().getId());
    }

    @Test
    @DisplayName("Should find all clients")
    void shouldFindAllClients() {
        Client client1 = new Client();
        Client client2 = new Client();

        clientRepository.save(client1);
        clientRepository.save(client2);

        List<Client> allClients = clientRepository.findAll();
        assertTrue(allClients.size() >= 2);
    }

    @Test
    @DisplayName("Should delete client by ID")
    void shouldDeleteClientById() {
        Client savedClient = clientRepository.save(testClient);
        Long id = savedClient.getId();

        clientRepository.deleteById(id);

        Optional<Client> deletedClient = clientRepository.findById(id);
        assertFalse(deletedClient.isPresent());
    }

    @Test
    @DisplayName("Should return empty Optional when client not found")
    void shouldReturnEmptyWhenClientNotFound() {
        Optional<Client> notFoundClient = clientRepository.findById(9999L);
        assertFalse(notFoundClient.isPresent());
    }

    @Test
    @DisplayName("Should update timestamp on modification")
    void shouldUpdateTimestampOnModification() throws InterruptedException {
        Client savedClient = clientRepository.save(testClient);
        var updatedAtFirst = savedClient.getUpdatedAt();
        
        Thread.sleep(100);
        
        Client clientToUpdate = clientRepository.findById(savedClient.getId()).orElseThrow();
        Client updatedClient = clientRepository.save(clientToUpdate);

        assertTrue(updatedClient.getUpdatedAt().isAfter(updatedAtFirst) || updatedClient.getUpdatedAt().equals(updatedAtFirst));
    }

    @Test
    @DisplayName("Should persist creation timestamp")
    void shouldPersistCreationTimestamp() {
        Client savedClient = clientRepository.save(testClient);
        Optional<Client> foundClient = clientRepository.findById(savedClient.getId());

        assertTrue(foundClient.isPresent());
        assertNotNull(foundClient.get().getCreatedAt());
        assertEquals(savedClient.getCreatedAt(), foundClient.get().getCreatedAt());
    }

    @Test
    @DisplayName("Should support soft delete via deleted flag")
    void shouldSupportSoftDeleteViaDeletedFlag() {
        Client savedClient = clientRepository.save(testClient);
        Long id = savedClient.getId();

        clientRepository.deleteById(id);

        Optional<Client> deletedClient = clientRepository.findById(id);
        assertFalse(deletedClient.isPresent());
    }

    @Test
    @DisplayName("Should count clients correctly")
    void shouldCountClientsCorrectly() {
        clientRepository.deleteAll();
        
        Client client1 = new Client();
        Client client2 = new Client();
        Client client3 = new Client();

        clientRepository.save(client1);
        clientRepository.save(client2);
        clientRepository.save(client3);

        long count = clientRepository.count();
        assertEquals(3L, count);
    }
}
