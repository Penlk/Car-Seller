package ru.penlk.dao.repositories.interfaces.cars;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.penlk.dao.entities.cars.Car;
import ru.penlk.dao.entities.cars.CarPart;

@Repository
public interface CarPartRepository extends JpaRepository<CarPart, Long> {
    @Modifying
    @Query("UPDATE CarPart e SET e.removed = true WHERE e.id = :id")
    void softDeleteById(@Param("id") Long id);

    default void delete(CarPart entity) {
        softDeleteById(entity.getId());
    }

    @Override
    default void deleteById(@Param("id") Long id) {
        softDeleteById(id);
    }
}