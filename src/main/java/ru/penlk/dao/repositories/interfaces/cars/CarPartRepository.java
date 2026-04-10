package ru.penlk.dao.repositories.interfaces.cars;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.penlk.dao.entities.cars.CarPart;

@Repository
public interface CarPartRepository extends JpaRepository<CarPart, Long> {
}