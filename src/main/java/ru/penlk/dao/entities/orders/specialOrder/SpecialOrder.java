package ru.penlk.dao.entities.orders.specialOrder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.penlk.dao.entities.BaseEntity;
import ru.penlk.dao.entities.cars.Car;
import ru.penlk.dao.entities.orders.specialAllowedParts.SpecialAllowedPart;
import ru.penlk.dao.entities.users.clients.Client;
import ru.penlk.dao.entities.users.managers.Manager;
import ru.penlk.dao.entities.valueObjects.Price;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "special_orders")
public class SpecialOrder extends BaseEntity {
    @Column(nullable = false)
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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "special_configurations",
            joinColumns = @JoinColumn(name = "special_order_id")
    )
    private Set<SpecialAllowedPart> specialConfigurations = new HashSet<>();

    @Column(nullable = false)
    private Price price;
}
