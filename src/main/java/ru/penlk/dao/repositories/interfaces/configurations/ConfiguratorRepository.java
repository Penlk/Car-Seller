package ru.penlk.dao.repositories.interfaces.configurations;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.penlk.dao.entities.configurations.specials.Configurator;

@Repository
public interface ConfiguratorRepository extends JpaRepository<Configurator, Long> {
    @Modifying
    @Query("UPDATE Configurator e SET e.removed = true WHERE e.id = :id")
    void softDeleteById(@Param("id") Long id);

    default void delete(Configurator entity) {
        softDeleteById(entity.getId());
    }

    @Override
    default void deleteById(@Param("id") Long id) {
        softDeleteById(id);
    }
}