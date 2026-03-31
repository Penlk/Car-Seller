package ru.penlk.dao.entities.orders.special;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.penlk.dao.entities.cars.CarPart;
import ru.penlk.dao.entities.cars.Car;
import ru.penlk.dao.entities.vo.Price;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@IdClass(SpecialAllowedPartId.class)
@Table(name = "special_allowed_parts")
public class SpecialAllowedPart {
    @Id
    @ManyToOne(optional = false)
    private Car car;

    @Id
    @ManyToOne(optional = false)
    private CarPart carPart;

    @Column
    private Price price;
}
