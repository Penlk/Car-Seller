package ru.penlk.dao.repositories.interfaces.orders.special;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.penlk.dao.entities.orders.special.SpecialOrder;

import java.util.List;

@Repository
public interface SpecialOrderRepository extends JpaRepository<SpecialOrder, Long> {
    List<SpecialOrder> findAllByOwnerId(String ownerId);

    List<SpecialOrder> findAllByManagerId(String managerId);

    @Modifying
    @Query("UPDATE SpecialOrder e SET e.removed = true WHERE e.id = :id")
    void softDeleteById(@Param("id") Long id);

    default void delete(SpecialOrder entity) {
        softDeleteById(entity.getId());
    }

    @Override
    default void deleteById(@Param("id") Long id) {
        softDeleteById(id);
    }
}