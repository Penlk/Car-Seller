package penlk.dao.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Formula;

@Getter
@Setter
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

    @Formula("reserved < stock")
    private boolean available;
}
