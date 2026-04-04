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
import ru.penlk.dao.entities.orders.special.SpecialAllowedPart;
import ru.penlk.dao.entities.orders.special.SpecialAllowedPartId;
import ru.penlk.dao.entities.orders.special.SpecialOrder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "special_configurations")
@IdClass(SpecialConfigurationId.class)
public class SpecialConfiguration {
    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "configurator_id")
    private Configurator configurator;

    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "car_part_id")
    private SpecialAllowedPart specialAllowedPart;
}
