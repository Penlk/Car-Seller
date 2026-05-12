package penlk.dao.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "car_stocks")
public class CarStock extends BaseEntity {
    @Column(nullable = false)
    private Long carSourceId;

    @Column(nullable = false)
    @PositiveOrZero
    private Long stock;

    @Column(nullable = false)
    @PositiveOrZero
    private Long reserved;

    @Column(nullable = false)
    private boolean available;
}
