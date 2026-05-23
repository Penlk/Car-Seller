package penlk.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import penlk.dao.entities.CarStock;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CarStockRepository extends JpaRepository<CarStock, UUID> {
    Optional<CarStock> findByCarSourceId(Long id);

    @Modifying
    @Query("UPDATE CarStock e SET e.removed = true WHERE e.id = :id")
    void softDeleteById(@Param("id") UUID id);

    default void delete(CarStock entity) {
        softDeleteById(entity.getId());
    }

    @Override
    default void deleteById(@Param("id") UUID id) {
        softDeleteById(id);
    }

    @Query("""
        SELECT cs
        FROM CarStock cs
        WHERE cs.removed = false
          AND cs.reserved < cs.stock
    """)
    List<CarStock> findAvailableCars();
}
