package ru.penlk.dao.entities.cars;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.type.PostgreSQLEnumJdbcType;
import ru.penlk.dao.entities.BaseEntity;
import ru.penlk.dao.entities.configurations.defaults.DefaultConfiguration;
import ru.penlk.dao.entities.nodes.RequireNode;
import ru.penlk.dao.entities.orders.special.SpecialAllowedPart;
import ru.penlk.dao.entities.vo.EnginePower;
import ru.penlk.dao.entities.vo.EngineVolume;
import ru.penlk.dao.entities.vo.Price;

import java.util.HashSet;
import java.util.Set;

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

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(columnDefinition = "fuel_type", nullable = false)
    private Fuel fuel;

    @Column(nullable = false)
    private EnginePower enginePower;

    @Column(nullable = false)
    private EngineVolume engineVolume;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(columnDefinition = "gear_shift_box_type", nullable = false)
    private GearShiftBox gearShiftBox;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(columnDefinition = "car_drive_type", nullable = false)
    private CarDrive carDrive;

    @Column(nullable = false)
    private String colour;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "car", cascade = CascadeType.ALL)
    private Set<DefaultConfiguration> defaultConfiguration = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "car", cascade = CascadeType.ALL)
    private Set<SpecialAllowedPart> specialAllowedParts = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "car", cascade = CascadeType.ALL)
    private Set<RequireNode> requireNodes = new HashSet<>();
}




