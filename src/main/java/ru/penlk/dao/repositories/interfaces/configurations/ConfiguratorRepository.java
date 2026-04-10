package ru.penlk.dao.repositories.interfaces.configurations;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.penlk.dao.entities.configurations.specials.Configurator;

@Repository
public interface ConfiguratorRepository extends JpaRepository<Configurator, Long> {
}