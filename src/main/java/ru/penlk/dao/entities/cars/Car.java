package ru.penlk.dao.entities.cars;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.penlk.dao.entities.BaseEntity;
import ru.penlk.dao.entities.carParts.CarPart;
import ru.penlk.dao.entities.nodes.Node;
import ru.penlk.dao.entities.orders.specialAllowedParts.SpecialAllowedPart;
import ru.penlk.dao.entities.requireNodes.RequireNode;
import ru.penlk.dao.entities.valueObjects.Price;

import java.util.HashSet;

import static aQute.bnd.annotation.headers.Resolution.optional;

@AllArgsConstructor
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "cars")
public class Car extends BaseEntity {
    @Column(nullable = false)
    private Price price;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private String body;

    @Column(nullable = false)
    private Fuel fuel;

    @Column(nullable = false)
    private EnginePower enginePower;

    @Column(nullable = false)
    private EngineVolume engineVolume;

    @Column(nullable = false)
    private GearShiftBox gearShiftBox;

    @Column(nullable = false)
    private CarDrive carDrive;

    @Column(nullable = false)
    private String colour;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "default_configurations")
    private HashSet<CarPart> defaultConfiguration = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "special_allowed_parts")
    private HashSet<SpecialAllowedPart> specialAllowedParts = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "require_nodes")
    private HashSet<RequireNode> requireNodes = new HashSet<>();
}




