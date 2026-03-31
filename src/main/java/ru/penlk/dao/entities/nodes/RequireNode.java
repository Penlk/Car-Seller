package ru.penlk.dao.entities.nodes;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.penlk.dao.entities.cars.Car;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "require_nodes")
@IdClass(RequireNodeId.class)
public class RequireNode {
    @Id
    @ManyToOne(optional = false)
    private Node node;

    @Id
    @ManyToOne(optional = false)
    private Car car;
}
