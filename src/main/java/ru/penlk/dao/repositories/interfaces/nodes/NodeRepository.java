package ru.penlk.dao.repositories.interfaces.nodes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.penlk.dao.entities.nodes.Node;

@Repository
public interface NodeRepository extends JpaRepository<Node, Long> {
}