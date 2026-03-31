package ru.penlk.dao.entities.carParts;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.penlk.dao.entities.BaseEntity;
import ru.penlk.dao.entities.nodes.Node;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "car_parts")
@NoArgsConstructor
public class CarPart extends BaseEntity {
    @Column(name = "name", nullable = false)
    private String namePart;

    @JoinColumn(name = "node_id", nullable = false)
    @ManyToOne(optional = false)
    private Node node;
}
