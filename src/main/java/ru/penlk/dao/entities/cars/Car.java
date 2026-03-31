package ru.penlk.dao.entities.cars;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.penlk.dao.entities.BaseEntity;
import ru.penlk.dao.entities.configurations.defaults.DefaultConfiguration;
import ru.penlk.dao.entities.orders.special.SpecialAllowedPart;
import ru.penlk.dao.entities.nodes.RequireNode;
import ru.penlk.dao.entities.vo.Price;

import java.util.HashSet;

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
    private HashSet<DefaultConfiguration> defaultConfiguration = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "special_allowed_parts")
    private HashSet<SpecialAllowedPart> specialAllowedParts = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "require_nodes")
    private HashSet<RequireNode> requireNodes = new HashSet<>();
}




