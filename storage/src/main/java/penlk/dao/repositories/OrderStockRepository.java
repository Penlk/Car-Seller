package penlk.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import penlk.dao.entities.orders.OrderStock;

import java.util.UUID;

public interface OrderStockRepository extends JpaRepository<OrderStock, UUID> {
    @Modifying
    @Query("UPDATE OrderStock e SET e.removed = true WHERE e.id = :id")
    void softDeleteById(@Param("id") UUID id);

    default void delete(OrderStock entity) {
        softDeleteById(entity.getId());
    }

    @Override
    default void deleteById(@Param("id") UUID id) {
        softDeleteById(id);
    }
}
