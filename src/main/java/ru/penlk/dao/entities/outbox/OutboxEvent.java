package ru.penlk.dao.entities.outbox;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.type.PostgreSQLEnumJdbcType;
import ru.penlk.dao.entities.BaseEntity;
import ru.penlk.dao.entities.cars.CarPart;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "outbox_events")
public class OutboxEvent extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(nullable = false)
    private OrderType orderType;

    @Column(nullable = false)
    private Long orderSourceId;

    @Column(nullable = false)
    Long carSourceId;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "outbox_event_car_parts",
            joinColumns = { @JoinColumn(name = "outbox_event_id") },
            inverseJoinColumns = { @JoinColumn(name = "car_part_id") }
    )
    Set<CarPart> carPartSources;
}
