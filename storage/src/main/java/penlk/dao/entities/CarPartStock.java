package penlk.dao.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "car_part_stocks")
public class CarPartStock extends BaseEntity {
    @Column(nullable = false)
    @PositiveOrZero
    private Long carPartSourceId;

    @Column(nullable = false)
    @PositiveOrZero
    private Long stock;

    @Column(nullable = false)
    @PositiveOrZero
    private Long reserved;

    @Column(nullable = false)
    private boolean available;
}
