package ru.penlk.dao.entities.configurations.defaults;

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
import ru.penlk.dao.entities.cars.Car;
import ru.penlk.dao.entities.cars.CarPart;

@AllArgsConstructor
@Getter
@Setter
@Entity
@NoArgsConstructor
@IdClass(DefaultConfigurationId.class)
@Table(name = "default_configurations")
public class DefaultConfiguration {
    @Id
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "car_part_id")
    private CarPart carPart;

    @Id
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "car_id")
    private Car car;
}
