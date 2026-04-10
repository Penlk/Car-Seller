package ru.penlk.dao.entities.configurations.specials;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.penlk.dao.entities.cars.CarPart;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@IdClass(ConfiguratorCarPartId.class)
@Table(name = "configurations_car_parts")
public class ConfiguratorCarPart {
    @Id
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "configurator_id")
    private Configurator configurator;

    @Id
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "car_part_id")
    private CarPart carPart;
}
