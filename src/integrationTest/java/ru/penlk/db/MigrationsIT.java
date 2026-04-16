package ru.penlk.db;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.penlk.config.AbstractIntegrationTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
class MigrationsIT extends AbstractIntegrationTest {

    @Autowired
    private DataSource dataSource;

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    void shouldCreateCarsTable() throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metadata = connection.getMetaData();
            ResultSet tables = metadata.getTables(null, null, "cars", null);
            assertTrue(tables.next(), "Таблица 'cars' не создана");
            assertEquals("cars", tables.getString("TABLE_NAME"));
        }
    }

    @Test
    void shouldCreateNodesTable() throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metadata = connection.getMetaData();
            ResultSet tables = metadata.getTables(null, null, "nodes", null);
            assertTrue(tables.next(), "Таблица 'nodes' не создана");
            assertEquals("nodes", tables.getString("TABLE_NAME"));
        }
    }

    @Test
    void shouldCreateCarPartsTable() throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metadata = connection.getMetaData();
            ResultSet tables = metadata.getTables(null, null, "car_parts", null);
            assertTrue(tables.next(), "Таблица 'car_parts' не создана");
            assertEquals("car_parts", tables.getString("TABLE_NAME"));
        }
    }

    @Test
    void shouldCreateCarsTableWithCorrectColumns() throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metadata = connection.getMetaData();
            ResultSet columns = metadata.getColumns(null, null, "cars", null);

            boolean hasId = false;
            boolean hasBrand = false;
            boolean hasModel = false;
            boolean hasPrice = false;
            boolean hasFuel = false;
            boolean hasEngineVolume = false;
            boolean hasEnginePower = false;
            boolean hasCreatedAt = false;
            boolean hasUpdatedAt = false;

            while (columns.next()) {
                String columnName = columns.getString("COLUMN_NAME");
                switch (columnName) {
                    case "id" -> hasId = true;
                    case "brand" -> hasBrand = true;
                    case "model" -> hasModel = true;
                    case "price" -> hasPrice = true;
                    case "fuel" -> hasFuel = true;
                    case "engine_volume" -> hasEngineVolume = true;
                    case "engine_power" -> hasEnginePower = true;
                    case "created_at" -> hasCreatedAt = true;
                    case "updated_at" -> hasUpdatedAt = true;
                }
            }

            assertTrue(hasId, "Колонка 'id' отсутствует");
            assertTrue(hasBrand, "Колонка 'brand' отсутствует");
            assertTrue(hasModel, "Колонка 'model' отсутствует");
            assertTrue(hasPrice, "Колонка 'price' отсутствует");
            assertTrue(hasFuel, "Колонка 'fuel' отсутствует");
            assertTrue(hasEngineVolume, "Колонка 'engine_volume' отсутствует");
            assertTrue(hasEnginePower, "Колонка 'engine_power' отсутствует");
            assertTrue(hasCreatedAt, "Колонка 'created_at' отсутствует");
            assertTrue(hasUpdatedAt, "Колонка 'updated_at' отсутствует");
        }
    }

    @Test
    void shouldCreateEnumTypes() throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            var stmt = connection.createStatement();
            var result = stmt.executeQuery("SELECT 1 FROM pg_type WHERE typname = 'fuel_type'");
            assertTrue(result.next(), "ENUM тип 'fuel_type' не создан");

            result = stmt.executeQuery("SELECT 1 FROM pg_type WHERE typname = 'gear_shift_box_type'");
            assertTrue(result.next(), "ENUM тип 'gear_shift_box_type' не создан");

            result = stmt.executeQuery("SELECT 1 FROM pg_type WHERE typname = 'car_drive_type'");
            assertTrue(result.next(), "ENUM тип 'car_drive_type' не создан");
        }
    }

    @Test
    void shouldCreateRequireNodesAssociationTable() throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metadata = connection.getMetaData();
            ResultSet tables = metadata.getTables(null, null, "require_nodes", null);
            assertTrue(tables.next(), "Таблица 'require_nodes' не создана");
        }
    }

    @Test
    void shouldHaveForeignKeyFromCarParts() throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metadata = connection.getMetaData();
            ResultSet fks = metadata.getImportedKeys(null, null, "car_parts");
            
            boolean hasNodeFk = false;
            while (fks.next()) {
                if ("nodes".equals(fks.getString("PKTABLE_NAME"))) {
                    hasNodeFk = true;
                    break;
                }
            }
            assertTrue(hasNodeFk, "Внешний ключ на таблицу 'nodes' не создан в 'car_parts'");
        }
    }
}
