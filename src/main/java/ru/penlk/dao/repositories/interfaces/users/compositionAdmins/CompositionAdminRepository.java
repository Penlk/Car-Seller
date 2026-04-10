package ru.penlk.dao.repositories.interfaces.users.compositionAdmins;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.penlk.dao.entities.users.compositionAdmins.CompositionAdmin;

@Repository
public interface CompositionAdminRepository extends JpaRepository<CompositionAdmin, Long> {
}