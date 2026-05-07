package ru.penlk.dao.repositories.interfaces.orders.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.penlk.dao.entities.orders.common.CommonOrder;

import java.util.List;

@Repository
public interface CommonOrderRepository extends JpaRepository<CommonOrder, Long> {
    List<CommonOrder> findAllByOwnerId(String ownerId);

    List<CommonOrder> findAllByManagerId(String managerId);

    @Modifying
    @Query("UPDATE CommonOrder e SET e.removed = true WHERE e.id = :id")
    void softDeleteById(@Param("id") Long id);

    default void delete(CommonOrder entity) {
        softDeleteById(entity.getId());
    }

    @Override
    default void deleteById(@Param("id") Long id) {
        softDeleteById(id);
    }
}
