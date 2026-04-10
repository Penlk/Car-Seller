package ru.penlk.dao.entities.orders.special;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.dialect.type.PostgreSQLEnumJdbcType;
import ru.penlk.dao.entities.BaseEntity;
import ru.penlk.dao.entities.cars.Car;
import ru.penlk.dao.entities.configurations.specials.Configurator;
import ru.penlk.dao.entities.users.clients.Client;
import ru.penlk.dao.entities.users.managers.Manager;
import ru.penlk.dao.entities.vo.Price;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "special_orders")
public class SpecialOrder extends BaseEntity {
    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(columnDefinition = "special_order_state", nullable = false)
    private SpecialOrderState state;

    @JoinColumn(name = "client_id", nullable = false)
    @ManyToOne(optional = false)
    private Client client;

    @JoinColumn(name = "manager_id", nullable = true)
    @ManyToOne(optional = true)
    private Manager manager;

    @JoinColumn(name = "car_id", nullable = false)
    @ManyToOne(optional = false)
    private Car car;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "configurator_id", nullable = false)
    private Configurator configurator;

    @Column(nullable = false)
    private Price price;
}
