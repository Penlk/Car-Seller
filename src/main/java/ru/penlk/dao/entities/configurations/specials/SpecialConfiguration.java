package ru.penlk.dao.entities.configurations.specials;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.penlk.dao.entities.cars.CarPart;
import ru.penlk.dao.entities.orders.special.SpecialAllowedPartId;
import ru.penlk.dao.entities.orders.special.SpecialOrder;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "node_sets")
@IdClass(SpecialAllowedPartId.class)
public class SpecialConfiguration {
    @Id
    @ManyToOne(optional = false)
    private SpecialOrder specialOrder;

    @Id
    @ManyToOne(optional = false)
    private CarPart carPart;
}
