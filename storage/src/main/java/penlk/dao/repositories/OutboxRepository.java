package penlk.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import penlk.dao.entities.Outbox;

import java.util.List;
import java.util.UUID;

public interface OutboxRepository extends JpaRepository<Outbox, UUID> {
    List<Outbox> findTop100ByOrderByCreatedAtAsc();
}
