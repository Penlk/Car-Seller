package ru.penlk.dao.entities.requireNodes;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.penlk.dao.entities.cars.CarId;
import ru.penlk.dao.entities.nodes.NodeId;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "require_nodes")
@IdClass(RequireNodeId.class)
public class RequireNode {
    @Id
    private Long nodeId;
    private Long carId;
}
