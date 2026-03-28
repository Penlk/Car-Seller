package ru.penlk.dao.entities.orders.commonConfigurations;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.penlk.dao.entities.carParts.CarPartId;
import ru.penlk.dao.entities.cars.CarId;

@AllArgsConstructor
@Getter
@Setter
@Entity
@NoArgsConstructor
public class DefaultConfiguration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long nodeId;
    @Id
    private Long carId;
}
