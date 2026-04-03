package ru.penlk.dao.repositories.interfaces.nodes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.penlk.dao.entities.nodes.Node;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface NodeRepository extends JpaRepository<Node, Long> { }