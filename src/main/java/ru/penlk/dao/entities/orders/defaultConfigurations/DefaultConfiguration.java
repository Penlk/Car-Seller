package ru.penlk.dao.entities.orders.defaultConfigurations;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.penlk.dao.entities.carParts.CarPart;
import ru.penlk.dao.entities.cars.Car;

@AllArgsConstructor
@Getter
@Setter
@Entity
@NoArgsConstructor
@IdClass(DefaultConfigurationId.class)
@Table(name = "default_configurations")
public class DefaultConfiguration {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    private CarPart carPart;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    private Car car;
}
