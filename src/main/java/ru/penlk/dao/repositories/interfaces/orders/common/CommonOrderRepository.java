package ru.penlk.dao.repositories.interfaces.orders.common;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.penlk.dao.entities.orders.common.CommonOrder;

public interface CommonOrderRepository extends JpaRepository<CommonOrder, Long> { }
