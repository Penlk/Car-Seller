package ru.penlk.dao.entities.nodes;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
public class RequireNodeId implements Serializable {
    private Long node;
    private Long car;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RequireNodeId that)) return false;
        return Objects.equals(node, that.node) &&
                Objects.equals(car, that.car);
    }

    @Override
    public int hashCode() {
        return Objects.hash(node, car);
    }
}
