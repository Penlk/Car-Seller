package penlk.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import penlk.dao.entities.CarPartStock;

import java.util.UUID;

public interface CarPartStockRepository extends JpaRepository<CarPartStock, UUID> {
    @Modifying
    @Query("UPDATE CarPartStock e SET e.removed = true WHERE e.id = :id")
    void softDeleteById(@Param("id") UUID id);

    default void delete(CarPartStock entity) {
        softDeleteById(entity.getId());
    }

    @Override
    default void deleteById(@Param("id") UUID id) {
        softDeleteById(id);
    }
}
