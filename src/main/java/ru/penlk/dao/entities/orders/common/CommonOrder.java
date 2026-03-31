package ru.penlk.dao.entities.orders.common;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.penlk.dao.entities.BaseEntity;
import ru.penlk.dao.entities.cars.Car;
import ru.penlk.dao.entities.users.clients.Client;
import ru.penlk.dao.entities.users.managers.Manager;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "common_orders")
public class CommonOrder extends BaseEntity {
    @Column(name = "state")
    private CommonOrderState state;

    @JoinColumn(name = "client_id", nullable = false)
    @ManyToOne(optional = false)
    private Client client;

    @JoinColumn(name = "manager_id")
    @ManyToOne(optional = true)
    private Manager manager;

    @JoinColumn(name = "car_id", nullable = false)
    @ManyToOne(optional = false)
    private Car car;
}
