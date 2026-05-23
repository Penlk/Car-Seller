package ru.penlk.dao.repositories.interfaces.outbox;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.penlk.dao.entities.outbox.OutboxEvent;

import java.util.Set;

public interface OutboxRepository extends JpaRepository<OutboxEvent, Long> {
    Set<OutboxEvent> findTop100ByOrderByCreatedAtAsc();
}
