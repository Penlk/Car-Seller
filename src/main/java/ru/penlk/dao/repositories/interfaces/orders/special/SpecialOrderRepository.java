package ru.penlk.dao.repositories.interfaces.orders.special;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.penlk.dao.entities.orders.special.SpecialOrder;

@Repository
public interface SpecialOrderRepository extends JpaRepository<SpecialOrder, Long> { }