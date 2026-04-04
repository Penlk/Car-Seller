package ru.penlk.dao.repositories.interfaces.orders.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.penlk.dao.entities.orders.common.CommonOrder;

@Repository
public interface CommonOrderRepository extends JpaRepository<CommonOrder, Long> { }
