package ru.penlk.dao.repositories.interfaces.users.systemAdmins;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.penlk.dao.entities.users.systemAdmins.SystemAdmin;

public interface SystemAdminRepository extends JpaRepository<SystemAdmin, Long> {
}