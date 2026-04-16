package ru.penlk.dao.repositories.interfaces.cars;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.penlk.dao.entities.cars.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long>, JpaSpecificationExecutor<Car> {
    @Modifying
    @Query("UPDATE Car e SET e.removed = true WHERE e.id = :id")
    void softDeleteById(@Param("id") Long id);

    default void delete(Car entity) {
        softDeleteById(entity.getId());
    }

    @Override
    default void deleteById(@Param("id") Long id) {
        softDeleteById(id);
    }
}