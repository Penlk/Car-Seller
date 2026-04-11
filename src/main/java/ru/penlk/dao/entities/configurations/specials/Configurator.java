package ru.penlk.dao.entities.configurations.specials;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.penlk.dao.entities.BaseEntity;
import ru.penlk.dao.entities.cars.Car;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "configurators")
public class Configurator extends BaseEntity {
    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id")
    private Car car;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "configurator", cascade = CascadeType.ALL)
    private Set<SpecialConfiguration> specialConfigurations = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "configurator", cascade = CascadeType.ALL)
    private Set<ConfiguratorCarPart> carParts = new HashSet<>();

    @Column
    private boolean isFinished;
}
