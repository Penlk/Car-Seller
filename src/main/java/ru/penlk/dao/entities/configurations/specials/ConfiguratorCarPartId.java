package ru.penlk.dao.entities.configurations.specials;

import java.io.Serializable;
import java.util.Objects;

public class ConfiguratorCarPartId implements Serializable {
    Long configurator;
    Long carPart;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConfiguratorCarPartId that)) return false;
        return Objects.equals(configurator, that.configurator) &&
                Objects.equals(carPart, that.carPart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(configurator, carPart);
    }
}
