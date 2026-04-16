package ru.penlk.dao.repositories.interfaces.nodes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.penlk.dao.entities.nodes.Node;

@Repository
public interface NodeRepository extends JpaRepository<Node, Long> {
    @Modifying
    @Query("UPDATE Node e SET e.removed = true WHERE e.id = :id")
    void softDeleteById(@Param("id") Long id);

    default void delete(Node entity) {
        softDeleteById(entity.getId());
    }

    @Override
    default void deleteById(@Param("id") Long id) {
        softDeleteById(id);
    }
}