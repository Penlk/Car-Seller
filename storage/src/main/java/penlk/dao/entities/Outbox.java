package penlk.dao.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import penlk.dao.entities.orders.OrderType;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Outbox extends BaseEntity {
    @Column(nullable = false)
    private OrderType orderType;

    @Column(nullable = false)
    private Long sourceOrderId;

    @Column(nullable = false)
    private String event;

    @Column
    private String explanation;
}
