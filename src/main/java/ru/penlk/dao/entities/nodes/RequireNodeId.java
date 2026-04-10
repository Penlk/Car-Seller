package ru.penlk.dao.entities.nodes;

import lombok.AllArgsConstructor;
import ru.penlk.dao.entities.configurations.specials.ConfiguratorCarPartId;

import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor
public class RequireNodeId implements Serializable {
    private Long node;
    private Long car;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RequireNodeId)) return false;
        RequireNodeId that = (RequireNodeId) o;
        return Objects.equals(node, that.node) &&
                Objects.equals(car, that.car);
    }

    @Override
    public int hashCode() {
        return Objects.hash(node, car);
    }
}
