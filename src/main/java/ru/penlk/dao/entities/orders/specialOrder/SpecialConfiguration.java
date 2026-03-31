package ru.penlk.dao.entities.orders.specialOrder;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.penlk.dao.entities.orders.specialAllowedParts.SpecialAllowedPartId;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "node_sets")
@IdClass(SpecialAllowedPartId.class)
public class SpecialConfiguration {
    @Id
    private Long specialOrderId;

    @Id
    private Long carPartId;
}
