package penlk.dao.entities.orders;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.type.PostgreSQLEnumJdbcType;
import penlk.dao.entities.BaseEntity;
import penlk.dao.entities.CarPartStock;
import penlk.dao.entities.CarStock;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_stocks")
public class OrderStock extends BaseEntity {
    @Column(nullable = false)
    private Long orderSourceId;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(nullable = false)
    private OrderStatus status;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "car_stock_id")
    private CarStock carStock;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "order_car_parts",
            joinColumns = { @JoinColumn(name = "order_stock_id") },
            inverseJoinColumns = { @JoinColumn(name = "car_part_stock_id") }
    )
    private Set<CarPartStock> carPartStocks = new HashSet<>();

    @Column(nullable = true)
    private String employeeId;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(nullable = false)
    private OrderType orderType;
}
