package ru.penlk.dao.repositories.interfaces.users.clients;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.penlk.dao.entities.users.clients.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}
