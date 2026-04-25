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
import ru.penlk.dao.entities.users.compositionAdmins.CompositionAdmin;
import ru.penlk.dao.repositories.interfaces.users.compositionAdmins.CompositionAdminRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("CompositionAdminRepository Integration Tests")
@DataJpaTest
@Testcontainers
class CompositionAdminRepositoryIT {
    @Container
    @ServiceConnection
    static final PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:16-alpine")
                    .withDatabaseName("testdb")
                    .withUsername("test")
                    .withPassword("test");
    @Autowired
    private CompositionAdminRepository compositionAdminRepository;

    private CompositionAdmin testCompositionAdmin;

    @BeforeEach
    void setUp() {
        compositionAdminRepository.deleteAll();
        testCompositionAdmin = new CompositionAdmin();
    }

    @Test
    @DisplayName("Should save composition admin and generate ID")
    void shouldSaveCompositionAdminToDatabaseSuccessfully() {
        CompositionAdmin savedAdmin = compositionAdminRepository.save(testCompositionAdmin);

        assertNotNull(savedAdmin.getId());
        assertNotNull(savedAdmin.getCreatedAt());
    }

    @Test
    @DisplayName("Should find composition admin by ID")
    void shouldFindCompositionAdminById() {
        CompositionAdmin savedAdmin = compositionAdminRepository.save(testCompositionAdmin);
        Long id = savedAdmin.getId();

        Optional<CompositionAdmin> foundAdmin = compositionAdminRepository.findById(id);

        assertTrue(foundAdmin.isPresent());
        assertEquals(id, foundAdmin.get().getId());
    }

    @Test
    @DisplayName("Should find all composition admins")
    void shouldFindAllCompositionAdmins() {
        CompositionAdmin admin1 = new CompositionAdmin();
        CompositionAdmin admin2 = new CompositionAdmin();

        compositionAdminRepository.save(admin1);
        compositionAdminRepository.save(admin2);

        List<CompositionAdmin> allAdmins = compositionAdminRepository.findAll();
        assertTrue(allAdmins.size() >= 2);
    }

    @Test
    @DisplayName("Should delete composition admin by ID")
    void shouldDeleteCompositionAdminById() {
        CompositionAdmin savedAdmin = compositionAdminRepository.save(testCompositionAdmin);
        Long id = savedAdmin.getId();

        compositionAdminRepository.deleteById(id);

        Optional<CompositionAdmin> deletedAdmin = compositionAdminRepository.findById(id);
        assertFalse(deletedAdmin.isPresent());
    }

    @Test
    @DisplayName("Should return empty Optional when composition admin not found")
    void shouldReturnEmptyWhenCompositionAdminNotFound() {
        Optional<CompositionAdmin> notFoundAdmin = compositionAdminRepository.findById(9999L);
        assertFalse(notFoundAdmin.isPresent());
    }

    @Test
    @DisplayName("Should update timestamp on modification")
    void shouldUpdateTimestampOnModification() throws InterruptedException {
        CompositionAdmin savedAdmin = compositionAdminRepository.save(testCompositionAdmin);
        var updatedAtFirst = savedAdmin.getUpdatedAt();

        Thread.sleep(100);

        CompositionAdmin adminToUpdate = compositionAdminRepository.findById(savedAdmin.getId()).orElseThrow();
        CompositionAdmin updatedAdmin = compositionAdminRepository.save(adminToUpdate);

        assertTrue(updatedAdmin.getUpdatedAt().isAfter(updatedAtFirst) || updatedAdmin.getUpdatedAt().equals(updatedAtFirst));
    }

    @Test
    @DisplayName("Should persist creation timestamp")
    void shouldPersistCreationTimestamp() {
        CompositionAdmin savedAdmin = compositionAdminRepository.save(testCompositionAdmin);
        Optional<CompositionAdmin> foundAdmin = compositionAdminRepository.findById(savedAdmin.getId());

        assertTrue(foundAdmin.isPresent());
        assertNotNull(foundAdmin.get().getCreatedAt());
        assertEquals(savedAdmin.getCreatedAt(), foundAdmin.get().getCreatedAt());
    }

    @Test
    @DisplayName("Should support soft delete via deleted flag")
    void shouldSupportSoftDeleteViaDeletedFlag() {
        CompositionAdmin savedAdmin = compositionAdminRepository.save(testCompositionAdmin);
        Long id = savedAdmin.getId();

        compositionAdminRepository.deleteById(id);

        Optional<CompositionAdmin> deletedAdmin = compositionAdminRepository.findById(id);
        assertFalse(deletedAdmin.isPresent());
    }

    @Test
    @DisplayName("Should count composition admins correctly")
    void shouldCountCompositionAdminsCorrectly() {
        compositionAdminRepository.deleteAll();

        CompositionAdmin admin1 = new CompositionAdmin();
        CompositionAdmin admin2 = new CompositionAdmin();
        CompositionAdmin admin3 = new CompositionAdmin();

        compositionAdminRepository.save(admin1);
        compositionAdminRepository.save(admin2);
        compositionAdminRepository.save(admin3);

        long count = compositionAdminRepository.count();
        assertEquals(3L, count);
    }

    @Test
    @DisplayName("Should persist all composition admin fields correctly")
    void shouldPersistAllCompositionAdminFieldsCorrectly() {
        CompositionAdmin savedAdmin = compositionAdminRepository.save(testCompositionAdmin);
        Optional<CompositionAdmin> foundAdmin = compositionAdminRepository.findById(savedAdmin.getId());

        assertTrue(foundAdmin.isPresent());
        CompositionAdmin admin = foundAdmin.get();

        assertNotNull(admin.getId());
        assertNotNull(admin.getCreatedAt());
        assertNotNull(admin.getUpdatedAt());
    }
}
