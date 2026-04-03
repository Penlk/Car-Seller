package ru.penlk.dao.repositories.interfaces.users.managers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.penlk.dao.entities.users.managers.Manager;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> { }
